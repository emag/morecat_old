package org.emamotor.morecat.admin.entry;

import am.ik.marked4j.Marked;
import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.admin.common.stereotype.View;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.model.EntryFormat;
import org.emamotor.morecat.model.EntryState;
import org.emamotor.morecat.model.Role;
import org.emamotor.morecat.model.User;
import org.emamotor.morecat.service.EntryService;
import org.emamotor.morecat.service.UserService;
import org.emamotor.morecat.util.DateUtil;
import org.slf4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@View
public class EntryEditController implements Serializable {

  @Inject
  private FacesContext facesContext;

  @Inject
  private Marked marked;

  @Inject
  private Logger logger;

  @Inject
  private EntryService entryService;

  @Inject
  private UserService userService;

  @Getter
  @Setter
  private Entry entry = new Entry();

  @Getter
  @Setter
  private boolean customPermalink;

  @Getter
  private String html;

  public void doFind() {

    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    User author = userService.findByName(request.getUserPrincipal().getName());

    // new entry
    if (entry.getId() == null
      || (this.entry = entryService.findById(entry.getId())) == null) {

      this.entry = new Entry();
      this.entry.setAuthor(author);
      this.entry.setFormat(EntryFormat.MARKDOWN);
      this.entry.setState(EntryState.DRAFT);

      return;
    }

    // existing entry
    // Authors can edit their own entries
    if (!request.isUserInRole(String.valueOf(Role.ADMIN))
      && !this.entry.getAuthor().equals(author)) {

      try {
        // TODO Should I redirect the user to Forbidden page?
        facesContext.getExternalContext().redirect(request.getContextPath() + "/mc-admin/overview/view.xhtml");
      } catch (IOException e) {
        logger.error("IOException occurred, Username : " + request.getUserPrincipal().getName(), e);
        facesContext.addMessage(null, new FacesMessage("System error!"));
      }

    }

  }

  public String doPublishOrUpdate() {

    String successMessage;
    switch (this.entry.getState()) {
      case DRAFT:
        this.entry.setState(EntryState.PUBLIC);
        successMessage = "Published!";
        break;
      case PUBLIC:
        successMessage = "Update!";
        break;
      default:
        throw new IllegalStateException("Invalid state");
    }

    if (!successCreateOrUpdate()) return null;

    facesContext.getExternalContext().getFlash().put("message", successMessage);

    return "view?faces-redirect=true";

  }

  private boolean successCreateOrUpdate() {
    List<String> errorMessages = createOrUpdate();
    if (errorMessages.size() > 0) {
      for (String errorMessage : errorMessages) {
        addFacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null);
      }
      return false;
    }

    logger.info("[{}]Edit entry {} by {} #" + DateUtil.getFormattedCurrentDateTime(),
      this.entry.getState(), this.entry.getTitle(), this.entry.getAuthor().getName());

    return true;
  }

  private List<String> createOrUpdate() {
    List<String> errorMessages = new ArrayList<>();
    try {
      entryService.update(this.entry);
    } catch (Exception e) {
      Throwable t;
      for (t = e.getCause(); t != null; t = t.getCause()) {
        if (t.getClass() == javax.persistence.PersistenceException.class) {
          errorMessages.add("The Permalink is already registered on the date. Please change the Permalink.");
        }
      }
    }
    return errorMessages;
  }

  private void addFacesMessage(FacesMessage.Severity severity, String summary, String detail) {
    facesContext.addMessage(null, new FacesMessage(severity, summary, detail));
  }

  public String doRevertToDraft() {
    this.entry.setState(EntryState.DRAFT);
    if (!successCreateOrUpdate()) return null;

    facesContext.getExternalContext().getFlash().put("message", "Revert to draft!");
    return "view?faces-redirect=true";
  }

  public String doSave() {
    if (!successCreateOrUpdate()) return null;

    facesContext.getExternalContext().getFlash().put("message", "Saved!");
    return "view?faces-redirect=true";
  }

  public void doPreview() {

    switch (this.entry.getFormat()) {
      case MARKDOWN:
        this.html = marked.marked(this.entry.getContent());
        break;
      case HTML:
        this.html = this.entry.getContent();
        break;
      default:
        throw new IllegalStateException("Invalid format");
    }

  }

  public void doChangeFormat() {
    doPreview();
  }

  public String getFormat_() {
    return this.entry.getFormat().name();
  }

  public void setFormat_(String selectedFormat) {
    this.entry.setFormat(EntryFormat.valueOf(selectedFormat));
  }

}

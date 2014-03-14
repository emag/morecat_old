package org.emamotor.morecat.admin.entry;

import am.ik.marked4j.Marked;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@Named
@ViewScoped
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
        if (! request.isUserInRole(String.valueOf(Role.ADMIN))
                && ! this.entry.getAuthor().equals(author)) {

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

        if (doCreateOrUpdate()) return null;

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
        facesContext.getExternalContext().getFlash().put("message", successMessage);

        return "view?faces-redirect=true";

    }

    private boolean doCreateOrUpdate() {
        List<String> errorMessages = createOrUpdate();
        for (String errorMessage : errorMessages) {
            addFacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null);
            return true;
        }

        logger.info("[{}]Edit entry {} by {} #" + DateUtil.getFormattedCurrentDateTime(),
                this.entry.getState(), this.entry.getTitle(), this.entry.getAuthor().getName());

        return false;
    }

    private List<String> createOrUpdate() {

        setupEntry();

        List<String> errorMessages = new ArrayList<>();
        if (validatePermalink()) {
            entryService.update(this.entry);
        } else {
            errorMessages.add("The Permalink is already registered on the date. Please change the Permalink.");
        }
        return errorMessages;

    }

    private void addFacesMessage(FacesMessage.Severity severity, String summary, String detail) {
        facesContext.addMessage(null, new FacesMessage(severity, summary, detail));
    }

    private void setupEntry() {
        if (StringUtils.isBlank(this.entry.getPermalink())) {
            this.entry.setPermalink(RandomStringUtils.randomNumeric(10));
        }
        if (this.entry.getCreatedAt() == null) {
            this.entry.setCreatedAt(new Date());
        }
    }

    private boolean validatePermalink() {
        for (Entry existingEntry : entryService.findAll()) {
            if (! existingEntry.getId().equals(this.entry.getId())
                    &&this.entry.getPermalink().equals(existingEntry.getPermalink())
                    && this.entry.getCreatedAt().equals(existingEntry.getCreatedAt())) {
                return false;
            }
        }
        return true;
    }

    public String doRevertToDraft() {

        if (doCreateOrUpdate()) return null;

        this.entry.setState(EntryState.DRAFT);
        facesContext.getExternalContext().getFlash().put("message", "Revert to draft!");

        return "view?faces-redirect=true";

    }

    public String doSave() {

        List<String> errorMessages = createOrUpdate();

        if (errorMessages.size() == 0) {
            facesContext.addMessage(null, new FacesMessage("Saved!"));
        } else {
            for (String errorMessage : errorMessages) {
                addFacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null);
            }
        }

        return null;
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

package org.emamotor.morecat.admin.entry;

import am.ik.marked4j.Marked;
import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.model.EntryFormat;
import org.emamotor.morecat.model.EntryState;
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
import java.io.Serializable;
import java.util.Date;

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

        // new entry
        if (entry.getId() == null
                || entryService.findById(entry.getId()) == null) {

            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            String loginUserName = request.getUserPrincipal().getName();
            this.entry.setAuthor(userService.findByName(loginUserName));
            this.entry.setFormat(EntryFormat.MARKDOWN);
            this.entry.setState(EntryState.DRAFT);

            return;
        }

        // existing entry
        this.entry = entryService.findById(entry.getId());

    }

    public String doPublishOrUpdate() {

        String message;
        switch (this.entry.getState()) {
            case DRAFT:
                this.entry.setState(EntryState.PUBLIC);
                message = "Published!";
                break;
            case PUBLIC:
                message = "Update!";
                break;
            default:
                throw new IllegalStateException("Invalid state");
        }

        createOrUpdate(message);
        return "view?faces-redirect=true";

    }

    public String doRevertToDraft() {
        this.entry.setState(EntryState.DRAFT);
        createOrUpdate("Revert to draft!");
        return "view?faces-redirect=true";
    }

    public String doSave() {
        createOrUpdate("Saved!");
        return null;
    }

    private void createOrUpdate(String message) {
        // new entry
        if (this.entry.getId() == null) {

            this.entry.setCreatedAt(new Date());
            entryService.create(this.entry);

        } else {
            // existing entry
            entryService.update(this.entry);
        }
        facesContext.addMessage(null, new FacesMessage(message));
        facesContext.getExternalContext().getFlash().put("message", message);
        logger.info("[{}]Edit entry {} by {} #" + DateUtil.getFormattedDateTime(this.entry.getCreatedAt()),
                this.entry.getState(), this.entry.getTitle(), this.entry.getAuthor().getName());
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

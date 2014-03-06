package org.emamotor.morecat.admin.entry;

import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.model.EntryFormat;
import org.emamotor.morecat.model.EntryState;
import org.emamotor.morecat.model.User;
import org.emamotor.morecat.service.EntryService;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Yoshimasa Tanabe
 */
@Named
@ViewScoped
public class EntryEditController implements Serializable {

    @Inject
    private EntryService entryService;

    @Getter
    @Setter
    private Entry entry = new Entry();

    @Getter
    @Setter
    private boolean customPermalink;

    public void doFind() {

        // new entry
        if (entry.getId() == null
                || entryService.findById(entry.getId()) == null) {

            entry.setAuthor(new User()); // FIXME
            entry.setFormat(EntryFormat.MARKDOWN);
            entry.setState(EntryState.DRAFT);

            return;
        }

        // existing entry
        this.entry = entryService.findById(entry.getId());

    }

    public String doPublish() {
        this.entry.setState(EntryState.PUBLIC);
        entryService.update(this.entry);
        return "view?faces-redirect=true";
    }

    public String doRevertToDraft() {
        this.entry.setState(EntryState.DRAFT);
        entryService.update(this.entry);
        return "view?faces-redirect=true";
    }

    public String doSave() {
        entryService.update(this.entry);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Saved!"));

        return null;
    }

    public String getFormat_() {
        return this.entry.getFormat().name();
    }

    public void setFormat_(String selectedFormat) {
        this.entry.setFormat(EntryFormat.valueOf(selectedFormat));
    }

}

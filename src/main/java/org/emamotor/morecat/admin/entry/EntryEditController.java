package org.emamotor.morecat.admin.entry;

import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.model.EntryFormat;
import org.emamotor.morecat.model.EntryState;
import org.emamotor.morecat.model.User;
import org.emamotor.morecat.service.EntryService;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 * @author Yoshimasa Tanabe
 */
@Model
public class EntryEditController {

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
        // TODO
        return "view?faces-redirect=true";
    }

    public String doSave() {
        entryService.update(this.entry);
        return null;
    }

    public String getFormat_() {
        return this.entry.getFormat().name();
    }

    public void setFormat_(String selectedFormat) {
        this.entry.setFormat(EntryFormat.valueOf(selectedFormat));
    }

}

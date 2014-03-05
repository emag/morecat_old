package org.emamotor.morecat.admin.entry;

import lombok.Getter;
import lombok.Setter;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.model.EntryFormat;
import org.emamotor.morecat.model.Setting;
import org.emamotor.morecat.service.EntryService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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

    public void doFind() {
        // TODO check if the id exists
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

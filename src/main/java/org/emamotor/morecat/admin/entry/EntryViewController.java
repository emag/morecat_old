package org.emamotor.morecat.admin.entry;

import lombok.Getter;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.service.EntryService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@Model
public class EntryViewController {

    @Inject
    private EntryService entryService;

    @Getter
    private List<Entry> entries;

    @PostConstruct
    public void init() {
        entries = entryService.findAll();
    }

}

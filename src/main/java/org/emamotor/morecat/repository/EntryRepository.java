package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.Entry;

/**
 * @author tanabe
 */
public class EntryRepository extends GenericRepository<Entry> {

    public EntryRepository() {
        super(Entry.class);
    }

}

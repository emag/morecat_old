package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.Entry;

import javax.ejb.Stateless;

/**
 * @author tanabe
 */
@Stateless
public class EntryRepository extends GenericRepository<Entry> {

    public EntryRepository() {
        super(Entry.class);
    }

}

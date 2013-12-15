package org.emamotor.morecat.service;

import org.emamotor.morecat.dao.EntryDAO;
import org.emamotor.morecat.model.Entry;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * @author tanabe
 */
@Model
public class EntryService {

    @Inject
    private EntryDAO entryDAO;

    public List<Entry> findAll() {
        return entryDAO.findAll();
    }

    public Entry findById(Integer id) {
        return entryDAO.findById(id);
    }

    public Entry create(Entry newEntry) {
        return entryDAO.create(newEntry);
    }

    public Entry update(Entry updateContent) {
        return entryDAO.update(updateContent);
    }

    public void delete(Integer id) {
        entryDAO.delete(id);
    }

}

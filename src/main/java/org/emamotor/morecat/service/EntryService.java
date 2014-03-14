package org.emamotor.morecat.service;

import org.emamotor.morecat.model.User;
import org.emamotor.morecat.repository.EntryRepository;
import org.emamotor.morecat.model.Entry;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * @author tanabe
 */
@Stateless
public class EntryService {

    @Inject
    private EntryRepository entryRepository;

    public List<Entry> findAll() {
        return entryRepository.findAll();
    }

    public List<Entry> findAllByAuthor(User author) {
        return entryRepository.findAllByAuthor(author);
    }

    public Entry findById(Integer id) {
        return entryRepository.findById(id);
    }

    public Entry create(Entry newEntry) {
        return entryRepository.create(newEntry);
    }

    public Entry update(Entry updateContent) {
        return entryRepository.update(updateContent);
    }

    public void delete(Integer id) {
        entryRepository.delete(id);
    }

}

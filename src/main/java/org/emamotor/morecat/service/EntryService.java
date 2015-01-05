package org.emamotor.morecat.service;

import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.repository.EntryRepository;
import org.emamotor.morecat.util.Pageable;
import org.emamotor.morecat.util.Pager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * @author tanabe
 */
@Stateless
public class EntryService {

  @Inject
  private EntryRepository entryRepository;

  public Pageable<Entry> findPageableAllPublished(int page, int size) {
    return entryRepository.findPageableAllPublished(page, size);
  }

  public Pager<Entry> findPagerPublishedByYearMonthDayPermalink(int year, int month, int day, String permalink) {
    return entryRepository.findPagerPublishedByYearMonthDayPermalink(year, month, day, permalink);
  }

  public List<Entry> findAll() {
    return entryRepository.findAll();
  }

  public List<Entry> findAllByAdmin() {
    return entryRepository.findAllByAdmin();
  }

  public List<Entry> findAllByAuthor(String authorName) {
    return entryRepository.findAllByAuthor(authorName);
  }

  public Entry findById(Integer id) {
    return entryRepository.findById(id);
  }

  public Set<String> findAllPublishedTags() {
    return entryRepository.findAllPublishedTags();
  }

  public Pageable<Entry> findAllPublishedByTag(String tag, int page, int size) {
    return entryRepository.findPageableAllPublishedByTag(tag, page, size);
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

package org.emamotor.morecat.service;

import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.model.User;
import org.emamotor.morecat.repository.EntryRepository;

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

  public List<Entry> findAllPublished(int start, int size) {
    return entryRepository.findAllPublished(start, size);
  }

  public List<Entry> findAllPublishedByYear(int year) {
    return entryRepository.findAllPublishedByYear(year);
  }

  public List<Entry> findAllPublishedByYearMonth(int year, int month) {
    return entryRepository.findAllPublishedByYearMonth(year, month);
  }

  public List<Entry> findAllPublishedByYearMonthDay(int year, int month, int day) {
    return entryRepository.findAllPublishedByYearMonthDay(year, month, day);
  }

  public Entry findPublishedByYearMonthDayPermalink(int year, int month, int day, String permalink) {
    return entryRepository.findPublishedByYearMonthDayPermalink(year, month, day, permalink);
  }

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

  public Set<String> findAllTags() {
    return entryRepository.findAllTags();
  }

  public List<Entry> findAllPublishedByTag(String tag) {
    return entryRepository.findAllPublishedByTag(tag);
  }
}

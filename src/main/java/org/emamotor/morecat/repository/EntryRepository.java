package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.model.EntryState;
import org.emamotor.morecat.model.Entry_;
import org.emamotor.morecat.model.User;
import org.emamotor.morecat.util.Pageable;
import org.emamotor.morecat.util.Pager;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author tanabe
 */
@Stateless
public class EntryRepository extends GenericRepository<Entry> {

  public EntryRepository() {
    super(Entry.class);
  }

  public List<Entry> findAllPublished() {
    setUpCriteria();

    cq.select(entry)
      .where(cb.equal(entry.get(Entry_.state), EntryState.PUBLIC))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

  public List<Entry> findAllPublished(int page, int size) {
    setUpCriteria();

    cq.select(entry)
      .where(cb.equal(entry.get(Entry_.state), EntryState.PUBLIC))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).setFirstResult(page * size).setMaxResults(size).getResultList();
  }

  public Pageable<Entry> findPageableAllPublished(int page, int size) {
    setUpCriteria();

    List<Entry> entries = findAllPublished(page, size);

    CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
    countQuery.select(cb.count(countQuery.from(Entry.class)));
    Long totalNumberOfEntries = getEntityManager().createQuery(countQuery).getSingleResult();

    return new Pageable<>(entries, totalNumberOfEntries, page, size);
  }

  public Entry findPublishedByYearMonthDayPermalink(int year, int month, int day, String permalink) {
    setUpCriteria();

    cq.select(entry).where(
      cb.equal(entry.get(Entry_.state), EntryState.PUBLIC),
      cb.equal(cb.function("year", Integer.class, entry.get(Entry_.createdDate)), year),
      cb.equal(cb.function("month", Integer.class, entry.get(Entry_.createdDate)), month),
      cb.equal(cb.function("day", Integer.class, entry.get(Entry_.createdDate)), day),
      cb.equal(entry.get(Entry_.permalink), permalink));

    Entry anEntry;
    try {
      anEntry = getEntityManager().createQuery(cq).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
    return anEntry;
  }

  public Pager<Entry> findPagerPublishedByYearMonthDayPermalink(int year, int month, int day, String permalink) {
    Entry anEntry = findPublishedByYearMonthDayPermalink(year, month, day, permalink);
    if (anEntry == null) {
      return null;
    }

    Pager<Entry> pager = new Pager<>();
    pager.setElement(anEntry);

    List<Entry> allPublished = findAllPublished();
    for (int i = 0; i < allPublished.size(); i++) {
      Entry target = allPublished.get(i);
      if (target.equals(anEntry)) {
        if (i  > 0) {
          pager.setNext(Optional.of(allPublished.get(i - 1)));
        } else {
          pager.setNext(Optional.empty());
        }
        if (i + 1 < allPublished.size()) {
          pager.setPrevious(Optional.of(allPublished.get(i + 1)));
        } else {
          pager.setPrevious(Optional.empty());
        }
      }
    }

    return pager;
  }

  public List<Entry> findAllByAdmin() {
    setUpCriteria();

    cq.select(entry)
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

  public List<Entry> findAllByAuthor(User author) {
    setUpCriteria();

    cq.select(entry)
      .where(cb.equal(entry.get(Entry_.author), author))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

  public Set<String> findAllPublishedTags() {
    List<Entry> publishedEntries = findAllPublished();
    Set<String> tags = new TreeSet<>();
    for (Entry publishedEntry : publishedEntries) {
      tags.addAll(publishedEntry.getTags());
    }
    return tags;
  }

  public List<Entry> findAllPublishedByTag(String tag, int page, int size) {
    setUpCriteria();

    cq.select(entry)
      .where(
        cb.equal(entry.get(Entry_.state), EntryState.PUBLIC),
        cb.isMember(tag, entry.get(Entry_.tags)))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).setFirstResult(page * size).setMaxResults(size).getResultList();
  }

  public Pageable<Entry> findPageableAllPublishedByTag(String tag, int page, int size) {
    setUpCriteria();

    List<Entry> entries = findAllPublishedByTag(tag, page, size);

    CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
    countQuery.select(cb.count(countQuery.from(Entry.class)))
      .where(
        cb.equal(entry.get(Entry_.state), EntryState.PUBLIC),
        cb.isMember(tag, entry.get(Entry_.tags))
      );
    Long totalNumberOfEntries = getEntityManager().createQuery(countQuery).getSingleResult();

    return new Pageable<>(entries, totalNumberOfEntries, page, size);
  }

}

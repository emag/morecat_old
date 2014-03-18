package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.model.EntryState;
import org.emamotor.morecat.model.Entry_;
import org.emamotor.morecat.model.User;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author tanabe
 */
@Stateless
public class EntryRepository extends GenericRepository<Entry> {

  public EntryRepository() {
    super(Entry.class);
  }

  public List<Entry> findAllPublished(int start, int size) {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<Entry> cq = cb.createQuery(Entry.class);
    Root<Entry> entry = cq.from(Entry.class);

    cq.select(entry)
      .where(cb.equal(entry.get(Entry_.state), EntryState.PUBLIC))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).setFirstResult(start).setMaxResults(size).getResultList();
  }

  public List<Entry> findAllPublishedByYear(int year) {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<Entry> cq = cb.createQuery(Entry.class);
    Root<Entry> entry = cq.from(Entry.class);

    cq.select(entry)
      .where(
        cb.equal(entry.get(Entry_.state), EntryState.PUBLIC),
        cb.equal(cb.function("year", Integer.class, entry.get(Entry_.createdDate)), year))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

  public List<Entry> findAllPublishedByYearMonth(int year, int month) {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<Entry> cq = cb.createQuery(Entry.class);
    Root<Entry> entry = cq.from(Entry.class);

    cq.select(entry)
      .where(
        cb.equal(entry.get(Entry_.state), EntryState.PUBLIC),
        cb.equal(cb.function("year", Integer.class, entry.get(Entry_.createdDate)), year),
        cb.equal(cb.function("month", Integer.class, entry.get(Entry_.createdDate)), month))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

  public List<Entry> findAllPublishedByYearMonthDay(int year, int month, int day) {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<Entry> cq = cb.createQuery(Entry.class);
    Root<Entry> entry = cq.from(Entry.class);

    cb.function("year", Integer.class, cb.currentDate());
    cq.select(entry)
      .where(
        cb.equal(entry.get(Entry_.state), EntryState.PUBLIC),
        cb.equal(cb.function("year", Integer.class, entry.get(Entry_.createdDate)), year),
        cb.equal(cb.function("month", Integer.class, entry.get(Entry_.createdDate)), month),
        cb.equal(cb.function("day", Integer.class, entry.get(Entry_.createdDate)), day))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

  public Entry findPublishedByYearMonthDayPermalink(int year, int month, int day, String permalink) {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<Entry> cq = cb.createQuery(Entry.class);
    Root<Entry> entry = cq.from(Entry.class);

    cb.function("year", Integer.class, cb.currentDate());
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

  public List<Entry> findAllByAuthor(User author) {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<Entry> cq = cb.createQuery(Entry.class);
    Root<Entry> entry = cq.from(Entry.class);

    cq.select(entry).where(cb.equal(entry.get(Entry_.author), author));

    return getEntityManager().createQuery(cq).getResultList();
  }

}

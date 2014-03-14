package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.model.Entry_;
import org.emamotor.morecat.model.User;
import org.emamotor.morecat.model.User_;

import javax.ejb.Stateless;
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

    public List<Entry> findAllByAuthor(User author) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Entry> cq = cb.createQuery(Entry.class);

        Root<Entry> entry = cq.from(Entry.class);
        cq.select(entry).where(cb.equal(entry.get(Entry_.author), author));

        return getEntityManager().createQuery(cq).getResultList();
    }

}

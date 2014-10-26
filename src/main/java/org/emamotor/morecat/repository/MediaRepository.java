package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.Media;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
@Stateless
public class MediaRepository extends GenericRepository<Media> {

  // TODO GenericRepository の findAll をそのまま使うと、java.lang.IllegalArgumentException: Not an entity: null が出る
  public List<Media> findAll() {
    CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
    cq.select(cq.from(Media.class));
    return getEntityManager().createQuery(cq).getResultList();
  }

  public Media create(Media newEntity) {
    getEntityManager().persist(newEntity);
    return newEntity;
  }

}

package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.Media;
import org.emamotor.morecat.model.Media_;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 * @author Yoshimasa Tanabe
 */
@Stateless
public class MediaRepository extends GenericRepository<Media> {

  public MediaRepository() {
    super(Media.class);
  }

  public Media find(String uuid, String fileName) {
    setUpCriteria();

    cq.select(entry)
      .where(
        cb.equal(entry.get(Media_.uuid), uuid),
        cb.equal(entry.get(Media_.name), fileName)
      );

    Media media;
    try {
      media = getEntityManager().createQuery(cq).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
    return media;
  }

  public Media create(Media newEntity) {
    getEntityManager().persist(newEntity);
    return newEntity;
  }

}

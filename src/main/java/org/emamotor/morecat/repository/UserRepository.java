package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.User;
import org.emamotor.morecat.model.User_;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Yoshimasa Tanabe
 */
@Stateless
public class UserRepository extends GenericRepository<User> {

  public UserRepository() {
    super(User.class);
  }

  public User findByEmail(String email) {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<User> cq = cb.createQuery(User.class);

    Root<User> user = cq.from(User.class);
    cq.select(user).where(cb.equal(user.get(User_.email), email));

    return getEntityManager().createQuery(cq).getSingleResult();
  }

}

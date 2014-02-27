package org.emamotor.morecat.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Yoshimasa Tanabe
 */
public abstract class GenericRepository<T> {

    @Inject
    private EntityManager em;

    private Class<T> entityClass;

    public GenericRepository() {}

    public GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    public List<T> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    public T findById(Integer id) {
        return em.find(entityClass, id);
    }

    @Transactional
    public T create(T newEntity) {
        em.persist(newEntity);
        return newEntity;
    }

    @Transactional
    public T update(T updateContent) {
        return em.merge(updateContent);
    }

    @Transactional
    public void delete(Integer id) {
        em.remove(em.find(entityClass, id));
    }

}

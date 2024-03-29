/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Jackson Tai
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    protected Optional<T> findResultByAttribute(String namedQuery, String paramName, String paramValue) {
        try {
            T entity = (T) getEntityManager().createNamedQuery(namedQuery)
                    .setParameter(paramName, paramValue)
                    .getSingleResult();
            return Optional.ofNullable(entity);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    protected Optional<List<T>> findResultsByAttribute(String namedQuery, String paramName, String paramValue) {
        try {
            List<T> entity = (List<T>) getEntityManager().createNamedQuery(namedQuery)
                    .setParameter(paramName, paramValue)
                    .getResultList();
            return Optional.ofNullable(entity);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    protected Optional<T> findResultByAttributes(String namedQuery, String[] paramNames, String[] paramValues) {
        if (paramNames.length != paramValues.length) {
            throw new IllegalArgumentException("The length of parameter names and values must match.");
        }
        try {
            if (paramNames.length != paramValues.length) {
                throw new IllegalArgumentException("Number of paramNames must match the number of paramValues");
            }
            javax.persistence.Query query = getEntityManager().createNamedQuery(namedQuery);
            for (int i = 0; i < paramNames.length; i++) {
                query.setParameter(paramNames[i], paramValues[i]);
            }
            T result = (T) query.getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<T> findResultsByAttributes(int[] range, String namedQuery, Map<String, String> parameters) {
        javax.persistence.Query query;

        if (namedQuery != null && parameters != null && !parameters.isEmpty()) {
            query = getEntityManager().createNamedQuery(namedQuery);
            parameters.forEach(query::setParameter);
        } else {
            javax.persistence.criteria.CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
            cq.select(cq.from(entityClass));
            query = getEntityManager().createQuery(cq);
        }

        query.setMaxResults(range[1] - range[0] + 1);
        query.setFirstResult(range[0]);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public int count(String namedQuery, Map<String, String> parameters) {
        javax.persistence.Query query;

        if (namedQuery != null && parameters != null && !parameters.isEmpty()) {
            query = getEntityManager().createNamedQuery(namedQuery);
            parameters.forEach(query::setParameter);
        } else {
            return count();
        }

        try {
            return ((Long) query.getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }
}

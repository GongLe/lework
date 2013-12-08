package org.lework.runner.orm;

import org.apache.commons.lang3.Validate;
import org.lework.runner.utils.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Jpa2.0 Abstract Common Crud Dao Class
 * User: Gongle
 * Date: 13-9-10
 */
public class CrudJpaDao<T, ID extends Serializable> {
    public CrudJpaDao() {
        this.entityClass = Reflections.getClassGenricType(getClass());
    }

    public CrudJpaDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    EntityManager entityManager;

    protected Class<T> entityClass;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Refresh a persistant object that may have changed in another thread/transaction.
     *
     * @param entity transient entity
     */
    public void refresh(final T entity) {
        entityManager.refresh(entity);
    }

    /**
     * Write to database anything that is pending operation and clear it.
     */
    public void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    public T load(ID id) throws EntityNotFoundException {
        T entity = get(id);
        if (entity == null) {
            throw new EntityNotFoundException("entity " + entityClass + "#" + id + " was not found");
        }
        return entity;
    }

    /**
     * @param id primary key
     */
    public T get(ID id) {
        return (T) entityManager.find(entityClass, id);

    }

    /**
     * 按JPQL查询对象列表.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> List<X> find(final String jpql, final Object... values) {
        logger.debug("find jpql: {} # param: {}", jpql, values);
        return createQuery(jpql, values).getResultList();
    }

    /**
     * 按JPQL查询对象列表.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> List<X> find(final String jpql, final Map<String, ?> values) {
        logger.debug("find jpql: {} # param: {}", jpql, values);
        return createQuery(jpql, values).getResultList();
    }

    /**
     * 按JPQL查询唯一对象.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> X findUnique(final String jpql, final Object... values) {
        logger.debug("findUnique jpql: {} # param: {}", jpql, values);
        List<X> result = createQuery(jpql, values).getResultList();
        if (result == null || result.isEmpty()) {
            return null;
        } else if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

    /**
     * 按JPQL查询唯一对象.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> X findUnique(final String jpql, final Map<String, ?> values) {
        logger.debug("findUnique jpql: {} # param: {}", jpql, values);
        List<X> result = createQuery(jpql, values).getResultList();
        if (result == null || result.isEmpty()) {
            return null;
        } else if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

    public Long count(final String jpql, final Object... values) {

        return (Long) createQuery(jpql, values).getSingleResult();
    }


    public List<T> findAll() {
        return this.findAllBetween(0, -1);
    }

    public List<T> findAllBetween(int first, int size) {
        CriteriaQuery<T> criteriaQuery = this.entityManager.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.from(entityClass);

        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        if (size > -1) {
            typedQuery.setMaxResults(size);  //设置返回的最大结果数；
            typedQuery.setFirstResult(first);
        }

        return typedQuery.getResultList();
    }


    /**
     * 执行JPQL进行批量修改/删除操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String jpql, final Object... values) {
        logger.debug("batchExecute jpql: {} # param: {}", jpql, values);
        return createQuery(jpql, values).executeUpdate();
    }

    /**
     * 执行JPQL进行批量修改/删除操作.
     *
     * @param values 命名参数,按别名绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String jpql, final Map<String, ?> values) {
        logger.debug("batchExecute jpql: {} # param: {}", jpql, values);
        return createQuery(jpql, values).executeUpdate();
    }


    /**
     * 按JPQL查询对象列表.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public Query createQuery(final String queryString, final Object... values) {
        Validate.notBlank(queryString, "queryString不能为空");
        Query query = getEntityManager().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i + 1, values[i]);
            }
        }
        return query;
    }

    /**
     * 根据查询JPQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param values 命名参数,按名称绑定.
     */
    public Query createQuery(final String queryString, final Map<String, ?> values) {
        Validate.notBlank(queryString, "queryString不能为空");
        Query query = getEntityManager().createQuery(queryString);
        if (values != null) {
            String k;
            String v;
            for (Iterator it = values.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry entry = (Map.Entry) it.next();
                k = entry.getKey().toString();
                v = entry.getValue().toString();
                query.setParameter(k, v);
            }
        }
        return query;
    }


}

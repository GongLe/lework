package org.lework.runner.orm;

import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Jpa2.0 Abstract Common Dao Class
 * User: Gongle
 * Date: 13-9-10
 */
@SuppressWarnings("unchecked")
public class JpaDao<T, ID extends Serializable> extends CrudJpaDao<T, ID> {


    /**=================
     * 分页操作封装
     * =================
     */
    /**
     * 按JPQL分页查询.
     *主表需要使用 x作为别名.
     *eg: select x from Role x inner join x.menus m  where m.id=?1
     * @param pageable Spring data jpa 分页对象
     * @param jpql     hql语句.
     * @param values   数量可变的查询参数,按顺序绑定.
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     * @see org.springframework.data.domain.Pageable
     * @see org.springframework.data.domain.Page
     */
    public Page<T> findPage(final Pageable pageable, String jpql, final Object... values) {
        Validate.notNull(pageable, "pageable不能为空");

        long totalCount = countHqlResult(jpql, values);
        jpql = setOrderParameterToHql(jpql, pageable);
        Query q = createQuery(jpql, values);
        setPageParameterToQuery(q, pageable);

        Page<T> page = new PageImpl<T>(q.getResultList(), pageable, totalCount);

        return page;
    }

    /**
     * 按JPQL分页查询.
     *主表需要使用 x作为别名.
     *eg: select x from Role x inner join x.menus m  where m.id=?1
     * @param pageable Spring data jpa 分页对象
     * @param jpql     hql语句.
     * @param values   数量可变的查询参数,按别名绑定.
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     * @see org.springframework.data.domain.Pageable
     * @see org.springframework.data.domain.Page
     */
    public Page<T> findPage(final Pageable pageable, String jpql, final Map<String, ?> values) {
        Validate.notNull(pageable, "pageable不能为空");

        long totalCount = countHqlResult(jpql, values);
        jpql = setOrderParameterToHql(jpql, pageable);
        Query q = createQuery(jpql, values);
        setPageParameterToQuery(q, pageable);

        Page<T> page = new PageImpl<T>(q.getResultList(), pageable, totalCount);

        return page;
    }

    /**
     * 在JPQL的后面添加分页参数定义的orderBy, 辅助函数.
     */
    protected String setOrderParameterToHql(final String jpql, final Pageable pageable) {
        if (pageable.getSort() == null)
            return jpql;
        StringBuilder builder = new StringBuilder(jpql);
        builder.append(" order by");

        Iterator<Order> it = pageable.getSort().iterator();

        while (it.hasNext()) {
            Order o = it.next();
            builder.append(String.format(" %s.%s %s,", "x", o.getProperty(), o.getDirection()));
        }

        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    /**
     * 设置分页参数到Query对象,辅助函数.
     */
    protected Query setPageParameterToQuery(final Query q, final Pageable pageable) {
        q.setFirstResult(pageable.getOffset());
        q.setMaxResults(pageable.getPageSize());
        return q;
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p/>
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    protected Long countHqlResult(final String jpql, final Object... values) {
        String countHql = prepareCountHql(jpql);

        try {
            Long count = count(countHql, values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException("jpql can't be auto count, jpql is:" + countHql, e);
        }
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p/>
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    protected long countHqlResult(final String jpql, final Map<String, ?> values) {
        String countHql = prepareCountHql(jpql);

        try {
            Long count = findUnique(countHql, values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException("jpql can't be auto count, jpql is:" + countHql, e);
        }
    }

    private String prepareCountHql(String orgHql) {
        String countHql = "select count (*) " + removeSelect(removeOrders(orgHql));
        return countHql;
    }

    private static String removeSelect(String jpql) {
        int beginPos = jpql.toLowerCase().indexOf("from");
        return jpql.substring(beginPos);
    }

    private static String removeOrders(String jpql) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(jpql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }
}

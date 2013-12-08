package org.lework.runner.orm.support;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.lework.runner.utils.Collections3;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

/**
 * Specification工具类,帮助通过SearchFilter和属性名创建Specification或者jpa2.0 Predicate
 * @see Predicate
 * @see SearchFilter
 *
 * @author Gongle
 */
@SuppressWarnings({"unchecked"})
public class Specifications {
    /**
     * 通过SearchFilter构建Spring data jpa Specification
     *
     * @param <T>Root Entity Class
     * @param filters 属性过滤器
     * @param clazz
     * @return
     */
    public static <T> Specification<T> build(final Collection<SearchFilter> filters, final Class<T> clazz) {
        return new Specification<T>() {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = buildPredicates((List<SearchFilter>) filters, root, builder);
                // 将所有条件用 and 联合起来
                if (predicates.size() > 0) {
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }

                return builder.conjunction();
            }

        };
    }

    /**
     * 通过SearchFilter构建Spring data jpa Specification
     *
     * @param <T>Root Entity Class
     * @param filter  属性过滤器
     * @param clazz
     * @return
     */
    public static <T> Specification<T> build(final SearchFilter filter, final Class<T> clazz) {
        return new Specification<T>() {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = buildPredicate(filter, root, builder);
                // 将所有条件用 and 联合起来
                if (predicate != null) {
                    return builder.and(predicate);
                }

                return builder.conjunction();
            }

        };
    }

    /**
     * 通过属性过滤器创建jpa Predicate*
     */
    protected static List<Predicate> buildPredicates(List<SearchFilter> filters, Root root, CriteriaBuilder builder) {
        List<Predicate> predicates = Lists.newArrayList();
        if (Collections3.isNotEmpty(filters)) {
            for (SearchFilter filter : filters) {
                if (!filter.hasMultiProperties()) { //只有一个属性需要比较的情况.
                    Predicate p = buildPredicate(filter, root, builder);

                    predicates.add(p);
                } else {//包含多个属性需要比较的情况,进行or处理.<rest of statement> AND (type = 1 OR type = 4 OR type = 7).
                    List<Predicate> nestedPredicates = Lists.newArrayList();

                    for (String param : filter.getPropertyNames()) {
                        Predicate p = buildPredicate(param, filter.getMatchValue(), filter.getMatchType(), root, builder);
                        nestedPredicates.add(p);

                    }
                    predicates.add(builder.or(nestedPredicates.toArray(new Predicate[nestedPredicates.size()])));
                }
            }

        }
        return predicates;
    }

    /**
     * 通过属性过滤器创建jpa Predicate*
     */
    protected static Predicate buildPredicate(SearchFilter filter, Root root, CriteriaBuilder builder) {

        return buildPredicate(filter.getPropertyName(), filter.getMatchValue(), filter.getMatchType(), root, builder);

    }

    /**
     * 通过CriteriaBuilder创建一个谓语查询
     *
     * @param propertyName 属性名
     * @param matchValue   属性值
     * @param builder      JPA CriteriaBuilder
     * @return JPA谓语
     */
    protected static Predicate buildPredicate(final String propertyName, final Object matchValue,
                                              final SearchFilter.MatchType matchType,
                                              Root root, CriteriaBuilder builder) {
        // ParameterExpression  p = builder.parameter(propertyType.getClass(), propertyName);

        Predicate predicate;

        String[] names = StringUtils.split(propertyName, ".");

        Path expression = root.get(names[0]);

        // nested path translate, 如Task的名为"user.name"的filedName,转换为Task.user.name属性
        for (int i = 1; i < names.length; i++) {
            expression = expression.get(names[i]);
        }

        // 匹配值
        switch (matchType) {
            case UEQ:
                predicate = builder.notEqual(expression, matchValue);
                break;
            case EQ:
                predicate = builder.equal(expression, matchValue);
                break;
            case LLIKE:
                predicate = builder.like(expression, "%" + matchValue);
                break;
            case LIKE:
                predicate = builder.like(expression, "%" + matchValue + "%");
                break;
            case RLIKE:
                predicate = builder.like(expression, matchValue + "%");
                break;
            case GT:
                predicate = builder.greaterThan(expression, (Comparable) matchValue);
            case LT:
                predicate = builder.lessThan(expression, (Comparable) matchValue);
                break;
            case GTE:
                predicate = builder.greaterThanOrEqualTo(expression, (Comparable) matchValue);
                break;
            case LTE:
                predicate = builder.lessThanOrEqualTo(expression, (Comparable) matchValue);
                break;
            default:
                //默认操作为等于(EQ)
                predicate = builder.equal(expression, matchValue);
        }
        return predicate;
    }


}

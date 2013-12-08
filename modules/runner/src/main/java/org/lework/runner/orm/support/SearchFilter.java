package org.lework.runner.orm.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.lework.runner.utils.Converts;
import org.lework.runner.web.Servlets;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 与具体ORM实现无关的属性过滤条件封装类, 主要记录页面中简单的搜索过滤条件.
 * <code> eg:  filter_EQS_name,filter_LIKES_name_OR_email  </code>
 *
 * <pre>
 *     MatchType :  UEQ, EQ, LLIKE, LIKE, RLIKE, GT, LT, GTE, LTE
 *     eg:
 *      List<SearchFilter> filters = Lists.newArrayList() ;
         if(Strings.isNotBlank(sSearch)){
            filters.add(new SearchFilter("LIKES_name_OR_code",sSearch));
         }
 * </pre>
 *
 * @author calvin
 * @author Gongle
 */
public class SearchFilter {

    /**
     * 多个属性间OR关系的分隔符.
     */
    public static final String OR_SEPARATOR = "_OR_";

    /**
     * 属性比较类型.
     */
    public enum MatchType {
        UEQ, EQ, LLIKE, LIKE, RLIKE, GT, LT, GTE, LTE
    }

    /**
     * 属性数据类型.
     */
    public enum PropertyType {
        S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

        private Class<?> clazz;

        private PropertyType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getValue() {
            return clazz;
        }
    }

    private MatchType matchType = null;
    private Object matchValue = null;

    private Class<?> propertyClass = null;
    private String[] propertyNames = null;

    public SearchFilter() {
    }

    /**
     * @param filterName 比较属性字符串,含待比较的比较类型、属性值类型及属性列表.
     *                   eg. LIKES_name_OR_loginName
     * @param value      待比较的值.
     */
    public SearchFilter(final String filterName, final String value) {

        String firstPart = StringUtils.substringBefore(filterName, "_");
        String matchTypeCode = StringUtils.substring(firstPart, 0, firstPart.length() - 1);
        String propertyTypeCode = StringUtils.substring(firstPart, firstPart.length() - 1, firstPart.length());

        try {
            matchType = Enum.valueOf(MatchType.class, matchTypeCode);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
        }

        try {
            propertyClass = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
        }

        String propertyNameStr = StringUtils.substringAfter(filterName, "_");
        Validate.isTrue(StringUtils.isNotBlank(propertyNameStr), "filter名称" + filterName + "没有按规则编写,无法得到属性名称.");
        propertyNames = StringUtils.splitByWholeSeparator(propertyNameStr, SearchFilter.OR_SEPARATOR);

        this.matchValue = Converts.convertStringToObject(value, propertyClass);
    }

    /**
     * 从HttpRequest中创建SearchFilter列表, 默认Filter属性名前缀为filter.
     *
     * @see #buildFromHttpRequest(javax.servlet.http.HttpServletRequest, String)
     */
    public static List<SearchFilter> buildFromHttpRequest(final HttpServletRequest request) {
        return buildFromHttpRequest(request, "filter");
    }

    /**
     * 从HttpRequest中创建SearchFilter列表
     * SearchFilter命名规则为Filter属性前缀_比较类型属性类型_属性名.
     * <p/>
     * eg.
     * filter_EQS_name
     * filter_LIKES_name_OR_email
     */
    public static List<SearchFilter> buildFromHttpRequest(final HttpServletRequest request, final String filterPrefix) {
        List<SearchFilter> filterList = new ArrayList<SearchFilter>();

        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> filterParamMap = Servlets.getParametersStartingWith(request, filterPrefix + "_");

        //分析参数Map,构造SearchFilter列表
        for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
            String filterName = entry.getKey();
            String value = (String) entry.getValue();
            //如果value值为空,则忽略此filter.
            if (StringUtils.isNotBlank(value)) {
                SearchFilter filter = new SearchFilter(filterName, value);
                filterList.add(filter);
            }
        }

        return filterList;
    }

    /**
     * 获取比较值的类型.
     */
    public Class<?> getPropertyClass() {
        return propertyClass;
    }

    /**
     * 获取比较方式.
     */
    public MatchType getMatchType() {
        return matchType;
    }

    /**
     * 获取比较值.
     */
    public Object getMatchValue() {
        return matchValue;
    }

    /**
     * 获取比较属性名称列表.
     */
    public String[] getPropertyNames() {
        return propertyNames;
    }

    /**
     * 获取唯一的比较属性名称.
     */
    public String getPropertyName() {
        Validate.isTrue(propertyNames.length == 1, "There are not only one property in this filter.");
        return propertyNames[0];
    }

    /**
     * 是否比较多个属性.
     */
    public boolean hasMultiProperties() {
        return (propertyNames.length > 1);
    }
}

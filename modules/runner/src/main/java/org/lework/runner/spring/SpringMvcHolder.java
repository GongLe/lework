package org.lework.runner.spring;


import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * spring mvc 上下文持有者，类似Struts2的ServletActionContext,
 *
 * @author Gongle
 */
@SuppressWarnings("unchecked")
public abstract class SpringMvcHolder {

    /**
     * 获取request attribute
     *
     * @param name 属性名称
     * @return Object
     */
    public static <T> T getRequestAttribute(String name) {
        return getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 设置request attribute
     *
     * @param name  属性名称
     * @param value 值
     */
    public static void addRequestAttribute(String name, Object value) {
        addAttribute(name, value, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 获取sessiont attribute
     *
     * @param name 属性名称
     * @return Object
     */
    public static <T> T getSessionAttribute(String name) {
        return getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 设置session attribute
     *
     * @param name  属性名称
     * @param value 值
     */
    public static void addSessionAttribute(String name, Object value) {
        addAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }


    /**
     * 根据作用域,获取Attribute
     *
     * @param name  attribute名称
     * @param scope 作用域,参考{@link RequestAttributes}
     * @return Object
     */
    public static <T> T getAttribute(String name, int scope) {
        return (T) getServletRequestAttributes().getAttribute(name, scope);
    }

    /**
     * 根据作用域,设置Attribute
     *
     * @param name  attribute名称
     * @param value 值
     * @param scope 作用域,参考{@link RequestAttributes}
     */
    public static void addAttribute(String name, Object value, int scope) {
        getServletRequestAttributes().setAttribute(name, value, scope);
    }

    /**
     * 获取HttpServletRequest
     *
     * @return {@link HttpServletRequest}
     */
    public static HttpServletRequest getRequest() {

        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取spring mvc的ServletRequestAttributes
     *
     * @return {@link ServletRequestAttributes}
     */
    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

    /**
     * 获取web项目中的真实路径
     *
     * @param path 指定的虚拟路径
     * @return String
     */
    public static String getRealPath(String path) {
        return getRequest().getSession().getServletContext().getRealPath(path);
    }
}
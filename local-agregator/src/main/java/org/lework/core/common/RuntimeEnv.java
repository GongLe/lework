package org.lework.core.common;


/**
 * APP runtime environment.
 *
 */
public enum RuntimeEnv {

    /**
     * Indicates APP runs on local (standard Servlet container).
     */
    LOCAL,
    /**
     * Indicates APP runs on <a href="http://code.google.com/appengine">
     * Google App Engine</a>.
     */
    GAE,
    /**
     * Indicates APP runs on <a href="http://developer.baidu.com/bae">Baidu App Engine</a>.
     */
    BAE
}
package org.lework.runner.cache.local;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;

public class EhCacheUtil {

    public static Ehcache getCache(CacheManager cacheManager, String cacheName) {
        if (cacheManager == null || StringUtils.isBlank(cacheName)) {
            return null;
        }
        return cacheManager.getEhcache(cacheName);
    }

    public static Element getElement(CacheManager cacheManager, String cacheName, String key) {
        if (cacheManager == null || StringUtils.isBlank(cacheName) || StringUtils.isBlank(key)) {
            return null;
        }
        Ehcache ehcache = getCache(cacheManager, cacheName);
        return ehcache == null ? null : ehcache.get(key);
    }

    public static Object getObjectValue(CacheManager cacheManager, String cacheName, String key) {
        if (cacheManager == null || StringUtils.isBlank(cacheName) || StringUtils.isBlank(key)) {
            return null;
        }
        Element element = getElement(cacheManager, cacheName, key);
        return element == null ? null : element.getObjectValue();
    }

    public static String getStringValue(CacheManager cacheManager, String cacheName, String key) {
        if (cacheManager == null || StringUtils.isBlank(cacheName) || StringUtils.isBlank(key)) {
            return null;
        }
        Element element = getElement(cacheManager, cacheName, key);
        return element == null ? null : String.valueOf(element.getValue());
    }

    public static void updateValue(CacheManager cacheManager, String cacheName, String key, Object value) {
        if (cacheManager == null || StringUtils.isBlank(cacheName) || StringUtils.isBlank(key)) {
            return;
        }
        Ehcache cache = getCache(cacheManager, cacheName);
        if (cache == null) {
            return;
        }
        if (value != null) cache.put(new Element(key, value));
        else invalidateValue(cacheManager, cacheName, key);
    }

    public static void updateValue(CacheManager cacheManager, String cacheName, String key, Object value, int timeToLive) {
        if (cacheManager == null || StringUtils.isBlank(cacheName) || StringUtils.isBlank(key)) {
            return;
        }
        Ehcache cache = getCache(cacheManager, cacheName);
        if (value != null) {
            Element element = new Element(key, value);
            element.setTimeToLive(timeToLive);
            element.setTimeToIdle(timeToLive / 2);
            cache.put(element);

        } else {
            invalidateValue(cacheManager, cacheName, key);
        }
    }

    public static void invalidateValue(CacheManager cacheManager, String cacheName, String key) {
        if (cacheManager == null || StringUtils.isBlank(cacheName) || StringUtils.isBlank(key)) {
            return;
        }
        Ehcache cache = getCache(cacheManager, cacheName);
        if (cache != null) cache.remove(key);
    }
}

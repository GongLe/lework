package org.lework.runner.cache.local;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.lework.runner.cache.Cache;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Map;

/**
 * EhCache adpater Bean
 * User: Gongle
 * Date: 13-12-20
 */
public class EhCacheAdpater implements Cache {
   // public static final String DEFAULT_CACHE_NAME = "DEFAULT_CACHE";

    private String cacheName ;
    private CacheManager  cacheManager;
    private   Ehcache ehcache  ;

    @PostConstruct
    private void init() {
        ehcache = EhCacheUtil.getCache(getCacheManager(), getCacheName());
    }

    @Override
    public Object get(String key) {
        return getElement(key) != null ? getElement(key).getObjectValue() : null;
    }

    @Override
    public boolean add(String key, Object value) {
        if (getElement(key) != null)
            return false;
        //add
        ehcache.put(new Element(key, value));
        return true;
    }

    @Override
    public boolean add(String key, Object value, Date expiry) {
        if (getElement(key) != null)
            return false;
        Number timeToLive = expiry.getTime();
        Element element = new Element(key, value);
        element.setTimeToLive(timeToLive.intValue());
        element.setTimeToIdle(timeToLive.intValue() / 2);
        ehcache.put(element);
        return true;
    }

    @Override
    public boolean add(String key, Object value, long expiry) {
        if (getElement(key) != null)
            return false;
        Number timeToLive = expiry;
        Element element = new Element(key, value);
        element.setTimeToLive(timeToLive.intValue());
        element.setTimeToIdle(timeToLive.intValue() / 2);
        ehcache.put(element);
        return true;
    }

    @Override
    public boolean replace(String key, Object value) {
        boolean ret = true;
        if (getElement(key) != null)
            ret = false;
        ehcache.put(new Element(key, value));
        return ret;
    }

    @Override
    public boolean replace(String key, Object value, Date expiry) {
        boolean ret = true;
        if (getElement(key) != null)
            ret = false;
        Number timeToLive = expiry.getTime();
        Element element = new Element(key, value);
        element.setTimeToLive(timeToLive.intValue());
        element.setTimeToIdle(timeToLive.intValue() / 2);
        return ret;
    }

    @Override
    public boolean replace(String key, Object value, long expiry) {
        boolean ret = true;
        if (getElement(key) != null)
            ret = false;
        Number timeToLive = expiry ;
        Element element = new Element(key, value);
        element.setTimeToLive(timeToLive.intValue());
        element.setTimeToIdle(timeToLive.intValue() / 2);
        return ret;
    }

    @Override
    public void delete(String key) {
        ehcache.remove(key);
    }

    @Override
    public void delete(String key, Date expiry) {
        //
        ehcache.remove(key);
    }

    @Override
    public void delete(String key, long expiry) {
        ehcache.remove(key);
    }

    @Override
    public Map<String, Object> getMulti(String[] keys) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, Object> getMultiArray(String[] keys) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean keyExists(String key) {

        return   ehcache.isKeyInCache(key);
    }
    //getter setter

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    private Element getElement(String key) {
        return ehcache.get(key);
    }
}

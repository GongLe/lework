package org.lework.runner.cache.bae;

import org.lework.runner.cache.Cache;
import org.springframework.beans.factory.FactoryBean;

import java.util.Date;
import java.util.Map;

/**
 * BEA cache adpater bean
 * User: Gongle
 * Date: 13-12-20
 */
public class BeaCacheAdpater implements Cache, FactoryBean<Cache> {
    public final static String DEFAULT_MEMCACHE_ADDR = "cache.duapp.com:20243";
    private String memcacheAddr = DEFAULT_MEMCACHE_ADDR;

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Class<Cache> getObjectType() {
        return Cache.class;
    }

    @Override
    public Cache getObject() throws Exception {
        //TODO 等待着bae sdk maven库
        // BaeCache baeCache = BaeFactory.getBaeCache(cacheId, memcacheAddr, user, password);
        return null;
    }

    /**
     * cacheId为资源id，memcacheAddr为cache的服务地址和端口（例如，cache.duapp.com:10240）, user为ak, password为sk*
     */
    private String cacheId;
    private String user;
    private String pwd;

    @Override
    public Object get(String key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean add(String key, Object value) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean add(String key, Object value, Date expiry) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean add(String key, Object value, long expiry) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean replace(String key, Object value) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean replace(String key, Object value, Date expiry) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean replace(String key, Object value, long expiry) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(String key) {
    }

    @Override
    public void delete(String key, Date expiry) {
    }

    @Override
    public void delete(String key, long expiry) {
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
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
    //getter setter

    public String getMemcacheAddr() {
        return memcacheAddr;
    }

    public void setMemcacheAddr(String memcacheAddr) {
        this.memcacheAddr = memcacheAddr;
    }

    public String getCacheId() {
        return cacheId;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}

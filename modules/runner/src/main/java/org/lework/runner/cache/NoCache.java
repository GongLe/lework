package org.lework.runner.cache;

import java.util.Date;
import java.util.Map;

/**
 * Do not cache
 */
public final class NoCache implements Cache {
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(String key, Date expiry) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(String key, long expiry) {
        //To change body of implemented methods use File | Settings | File Templates.
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
}

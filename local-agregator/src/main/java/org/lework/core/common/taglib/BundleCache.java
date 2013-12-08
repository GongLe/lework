package org.lework.core.common.taglib;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * User: Gongle
 * Date: 13-11-28
 */
public final  class BundleCache {
    //使用GuavaCache,最大缓存个数:100,过期时间:3小时
    public static final LoadingCache<String, String> CACHE;

    static {
        CACHE = CacheBuilder.newBuilder().maximumSize(100)
                .expireAfterAccess(3, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key;
                    }
                });
    }

    public static String get(String key) {
        String ret = null;
        try {
            ret = CACHE.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void update(String key, String value) {
        CACHE.put(key, value);
    }
    public static void invalidate(String key){
        CACHE.invalidate(key);
    }
}

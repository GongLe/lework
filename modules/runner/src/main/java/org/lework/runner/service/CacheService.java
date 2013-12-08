package org.lework.runner.service;

import java.util.List;

/**
 * Cache Adpater Service
 * User: Gongle
 * Date: 13-10-2
 */
public interface CacheService {
    public static final long ONE_SECOND = 1000;

    public static final long ONE_MINUTE = 60 * 1000;

    public static final long ONE_HOUR = ONE_MINUTE * 60;

    public static final Long ONE_DAY = ONE_HOUR * 24;

    /**
     * 默认缓存过期时间：1分钟
     */
    public static final long DEFAULT_EXPIRY = ONE_MINUTE;

    /**
     * 保存所有cache key所用到的cache的标识
     */
    public static final String KEYS_CACHE = "CACHE_KEYS_LE_KEYS";

    /**
     * 获取应用的Cache前缀
     *
     * @return
     */
    public String getAppCachePrefix();

    /**
     * 获取cache key
     *
     * @param key
     * @return
     */
    public String getKey(String key);

    /**
     * 将key-value存储在cache中，如果key已经存在，则旧的value将会被当前value所替代；如果key不存在，
     * 则将value存入cache
     *
     * @param key
     * @param value
     */
    public void updateCacheValue(String key, Object value);

    /**
     * 将key-value存储在cache中，如果key已经存在，则旧的value将会被当前value所替代；如果key不存在，
     * 则将value存入cache
     *
     * @param key
     * @param value
     * @param expiry 缓存时间（单位：毫秒）
     */
    public void updateCacheValue(String key, Object value, long expiry);

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public Object getCacheValue(String key);

    /**
     * 删除缓存
     *
     * @param key
     */
    public void deleteCache(String key);

    public List<String> getCacheKeys();
}

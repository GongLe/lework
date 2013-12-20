package org.lework.runner.cache;

/**
 * Cache Adpater Interface
 * User: Gongle
 *
 */
public interface Cache  {
    /**
     * 从cache中取回key对应的值，如果key不存在则返回null
     *
     * @param key
     */
    Object get(final String key);

    /**
     * 将key-value存储在cache中，如果key已经存在，则返回false，新的value不会替换旧的value
     *
     * @param key
     * @param value
     */
    boolean add(String key, Object value);

    /**
     * 将key-value存储在cache中，如果key已经存在，则返回false，新的value不会替换旧的value
     *
     * @param key
     * @param value
     */
    boolean add(String key, Object value, java.util.Date expiry);

    /**
     * 将key-value存储在cache中，如果key已经存在，则返回false，新的value不会替换旧的value
     *
     * @param key
     * @param value
     */
    boolean add(String key, Object value, long expiry);

    /**
     * 更新cache中key所对应的value，如果key不存在，则返回false
     *
     * @param key
     * @param value
     */
    boolean replace(String key, Object value);

    /**
     * 更新cache中key所对应的value，如果key不存在，则返回false
     *
     * @param key
     * @param value
     */
    boolean replace(String key, Object value, java.util.Date expiry);

    /**
     * 更新cache中key所对应的value，如果key不存在，则返回false
     *
     * @param key
     * @param value
     */
    boolean replace(String key, Object value, long expiry);

    /**
     * 给定key，从cache中将其对应的对象删除
     *
     * @param key
     */
    void delete(String key);

    /**
     * 给定key，从cache中将其对应的对象删除，可以延时删除，此方法是用于兼容Memcached客户端的方法 在进行延时删除时，比如设置的延迟删除时间是1s，那么在这1s内，对于add、replace和get操作是不会生效的，对于set操作是会生效的
     *
     * @param key
     */
    void delete(String key, java.util.Date expiry);

    /**
     * 给定key，从cache中将其对应的对象删除，可以延时删除，此方法是用于兼容Memcached客户端的方法 在进行延时删除时，比如设置的延迟删除时间是1s，那么在这1s内，对于add、replace和get操作是不会生效的，对于set操作是会生效的
     *
     * @param key
     */
    void delete(String key, long expiry);

    /**
     * 从cache中获取多个对象，一次最多64个
     *
     * @param keys
     */
    java.util.Map<String, Object> getMulti(String[] keys);

    /**
     * 从cache中获取多个对象，一次最多64个
     *
     * @param keys
     */
    java.util.Map<String, Object> getMultiArray(String[] keys);

    /**
     * 检查key是否在cache中存在
     *
     * @param key
     */
    boolean keyExists(String key);
}

package com.ljh.cache;

import com.ljh.util.JedisUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;

/**
 * RedisCache
 *
 * @author ljh
 * created on 2021/3/1 17:17
 */
@Component
public class RedisCache<K, V> implements Cache<K, V> {

    @Resource
    private JedisUtils jedisUtils;

    private final String CACHE_PREFIX = "shiro-cache:";

    private byte[] getKey(K k) {
        if (k instanceof String) {
            return (CACHE_PREFIX + k).getBytes(StandardCharsets.UTF_8);
        }
        return SerializationUtils.serialize(k);
    }


    @Override
    public V get(K k) throws CacheException {
        System.out.println("从 redis cache 获取数据");
        byte[] value = jedisUtils.get(getKey(k));
        if (value != null) {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtils.set(key, value);
        jedisUtils.expire(key, 600L);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = jedisUtils.get(key);
        jedisUtils.del(key);
        if (value != null) {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}

package com.ljh.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * JedisUtil
 *
 * @author ljh
 * created on 2021/3/1 14:22
 */
@Component
public class JedisUtils {

    @Resource
    private JedisPool jedisPool;

    private Jedis getResource() {
        return jedisPool.getResource();
    }

    public byte[] set(byte[] key, byte[] value) {
        try (Jedis jedis = getResource()) {
            jedis.set(key, value);
            return value;
        }
    }

    public void expire(byte[] key, long i) {
        try (Jedis jedis = getResource()) {
            jedis.expire(key, i);
        }
    }

    public byte[] get(byte[] key) {
        try (Jedis jedis = getResource()) {
            return jedis.get(key);
        }
    }

    public void del(byte[] key) {
        try (Jedis jedis = getResource()) {
            jedis.del(key);
        }
    }

    public Set<byte[]> keys(String shiro_session_prefix) {
        try (Jedis jedis = getResource()) {
            return jedis.keys((shiro_session_prefix + "*").getBytes(StandardCharsets.UTF_8));
        }
    }
}

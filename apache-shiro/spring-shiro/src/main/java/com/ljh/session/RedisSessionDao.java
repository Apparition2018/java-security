package com.ljh.session;

import com.ljh.util.JedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * RedisSessionDao
 * 使用 Redis 重写 SessionDao
 *
 * @author com.ljh
 * created on 2021/3/1 14:19
 */
public class RedisSessionDao extends AbstractSessionDAO {

    @Resource
    private JedisUtils jedisUtils;

    private final String SHIRO_SESSION_PREFIX = "shiro-session:";

    private byte[] getKey(String key) {
        return (SHIRO_SESSION_PREFIX + key).getBytes(StandardCharsets.UTF_8);
    }

    private void saveSession(Session session) {
        if (session != null && session.getId() != null) {
            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtils.set(key, value);
            jedisUtils.expire(key, 600);
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("read session");
        if (sessionId == null) {
            return null;
        }
        byte[] key = getKey(sessionId.toString());
        byte[] value = jedisUtils.get(key);
        return (Session) SerializationUtils.deserialize(value);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        byte[] key = getKey(session.getId().toString());
        jedisUtils.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = jedisUtils.keys(SHIRO_SESSION_PREFIX);
        Set<Session> sessions = new HashSet<>();
        if (CollectionUtils.isEmpty(keys)) {
            return sessions;
        }
        for (byte[] key : keys) {
            Session session = (Session) SerializationUtils.deserialize(jedisUtils.get(key));
            sessions.add(session);
        }
        return sessions;
    }
}

package com.module.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;

public enum RedisUtil {
    INSTANCE;

    private final JedisPool pool;

    RedisUtil() {
        pool = new JedisPool(new JedisPoolConfig(), "localhost",6379);
    }

    public boolean sadd(String key, String value) {
        Jedis jedis = null;
        Long flag= null;
        try {
            jedis = pool.getResource();
            flag =jedis.sadd(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return flag==1;
    }

    public boolean srem(String key, String value) {
        Jedis jedis = null;
        Long flag = null;
        try {
            jedis = pool.getResource();
          flag = jedis.srem(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return flag==1;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}

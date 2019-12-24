package cn.zy.userprofile.realtime.dw.dao.redis;

import cn.zy.userprofile.realtime.dw.common.utils.ConfigJavaUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Map;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/17 17:34
 * <p>
 * Jedis哨兵模式
 */
public class JedisSentinelUtils {

    private static JedisPoolConfig poolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        Map<String, String> configMap = ConfigJavaUtils.getPropertiesMap(JedisSentinelUtils.class, "/redis-conf.properties");

        config.setMaxTotal(Integer.valueOf(configMap.get(RedisConstant.REDIS_POOL_MAXTOTAL)));
        config.setMaxIdle(Integer.valueOf(configMap.get(RedisConstant.REDIS_POOL_MAXIDLE)));
        config.setMinIdle(Integer.valueOf(configMap.get(RedisConstant.REDIS_POOL_MINIDLE)));
        config.setMaxWaitMillis(Integer.valueOf(configMap.get(RedisConstant.REDIS_POOL_MAXWAITMILLIS)));
        config.setTestOnBorrow(Boolean.valueOf(configMap.get(RedisConstant.REDIS_POOL_TESTONBORROW)));
        config.setTestOnReturn(Boolean.valueOf(configMap.get(RedisConstant.REDIS_POOL_TESTONRETURN)));
        config.setTestWhileIdle(Boolean.valueOf(configMap.get(RedisConstant.REDIS_POOL_TESTWHILEIDLE)));
        config.setNumTestsPerEvictionRun(Integer.valueOf(configMap.get(RedisConstant.REDIS_POOL_NUMTESTSPEREVICTIONRUN)));
        config.setTimeBetweenEvictionRunsMillis(Long.valueOf(configMap.get(RedisConstant.REDIS_POOL_TIMEBETWEENEVICTIONRUNSMILLIS)));

        return config;
    }

    public static JedisSentinelPool jedisSentinelPool() {

        return null;
    }
}

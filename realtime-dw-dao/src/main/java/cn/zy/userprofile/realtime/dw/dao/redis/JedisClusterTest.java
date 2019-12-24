package cn.zy.userprofile.realtime.dw.dao.redis;

import cn.zy.userprofile.realtime.dw.common.utils.SpringContextHolder;
import redis.clients.jedis.JedisCluster;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/17 17:02
 */
public class JedisClusterTest {

    public static JedisCluster jedisCluster;

    static {
        try {
            jedisCluster = (JedisCluster) SpringContextHolder.getBean("jedisCluster");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package cn.zy.userprofile.realtime.dw.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.concurrent.*;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/17 16:00
 * <p>
 * Jedis
 */
public class JedisUtils {

    public static final Logger logger = LoggerFactory.getLogger(JedisUtils.class);

    public static void flushDB(Jedis jedis) {
        Long dbSize = jedis.dbSize();
        if (dbSize >= 0) {
            jedis.flushDB();
        }
    }

    public static void clear(Integer db, Jedis jedis) {
        long startTime = System.currentTimeMillis() / 1000;
        String result = "FALSE";

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> feature = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                jedis.select(db);
                flushDB(jedis);
                return "OK";
            }
        });

        try {
            result = feature.get(5, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        long endTime = System.currentTimeMillis() / 1000;

        if ("OK".equals(result)) {
            logger.info("Clear Redis DB using times:" + (endTime - startTime) + ", Clear db success");
        } else {
            logger.info("Clear Redis DB using times:" + (endTime - startTime) + ", Clear db failed");
        }
    }
}

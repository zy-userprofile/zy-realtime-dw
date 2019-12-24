package cn.zy.userprofile.realtime.dw.dao.redis;


/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/17 17:37
 */
public class RedisConstant {

    /********* 哨兵模式配置信息 *********/
    public static final String REDIS_POOL_MAXTOTAL = "redis_pool_maxtotal";
    public static final String REDIS_POOL_MAXIDLE = "redis_pool_maxidle";
    public static final String REDIS_POOL_MINIDLE = "redis_pool_minidle";
    public static final String REDIS_POOL_MAXWAITMILLIS = "redis_pool_maxwaitmillis";
    public static final String REDIS_POOL_TESTONBORROW = "redis_pool_testonborrow";
    public static final String REDIS_POOL_TESTONRETURN = "redis_pool_testonreturn";
    public static final String REDIS_POOL_TESTWHILEIDLE = "redis_pool_testwhileidle";
    public static final String REDIS_POOL_NUMTESTSPEREVICTIONRUN = "redis_pool_numtestsperevictionrun";
    public static final String REDIS_POOL_TIMEBETWEENEVICTIONRUNSMILLIS = "redis_pool_timebetweenevictionrunsmillis";
}

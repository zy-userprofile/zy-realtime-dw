package cn.zy.userprofile.realtime.dw.spark.common.conf;

import cn.zy.userprofile.realtime.dw.common.utils.ConfigUtils;
import cn.zy.userprofile.realtime.dw.spark.common.constant.HbaseConstant;

import java.util.Map;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/24 14:46
 */
public class HbaseConf {
    private static final Map<String, String> configMap = ConfigUtils.getPropertiesJavaMap(HbaseConf.class, "/hbase.properties");

    public static String HBASE_ZOOKEEPER_QUORUM = configMap.get(HbaseConstant.HBASE_ZOOKEEPER_QUORUM);
    public static String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = configMap.get(HbaseConstant.HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT);
    public static String HBASE_CLIENT_RETRIES_NUMBER = configMap.get(HbaseConstant.HBASE_CLIENT_RETRIES_NUMBER);
    public static String HBASE_RPC_TIMEOUT = configMap.get(HbaseConstant.HBASE_RPC_TIMEOUT);
    public static String HBASE_CLIENT_OPERATION_TIMEOUT = configMap.get(HbaseConstant.HBASE_CLIENT_OPERATION_TIMEOUT);
    public static String ZOOKEEPER_SESSION_TIMEOUT = configMap.get(HbaseConstant.ZOOKEEPER_SESSION_TIMEOUT);
    public static String ZOOKEEPER_ZNODE_PARENT = configMap.get(HbaseConstant.ZOOKEEPER_ZNODE_PARENT);
}

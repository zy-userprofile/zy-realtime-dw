package cn.zy.userprofile.realtime.dw.spark.common.constant;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/24 14:44
 * hbase相关配置常量
 */
public interface HbaseConstant {

    /****** hbase相关配置常量 ******/
    String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
    String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = "hbase.zookeeper.property.clientPort";
    String HBASE_CLIENT_RETRIES_NUMBER = "hbase.client.retries.number";
    String HBASE_RPC_TIMEOUT = "hbase.rpc.timeout";
    String HBASE_CLIENT_OPERATION_TIMEOUT = "hbase.client.operation.timeout";
    String ZOOKEEPER_SESSION_TIMEOUT = "zookeeper.session.timeout";
    String ZOOKEEPER_ZNODE_PARENT = "zookeeper.znode.parent";
}

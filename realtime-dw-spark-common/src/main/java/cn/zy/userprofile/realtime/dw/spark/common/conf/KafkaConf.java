package cn.zy.userprofile.realtime.dw.spark.common.conf;

import cn.zy.userprofile.realtime.dw.common.utils.ConfigUtils;
import cn.zy.userprofile.realtime.dw.spark.common.constant.KafkaConstant;

import java.util.Map;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/24 15:05
 */
public class KafkaConf {
    private static final Map<String, String> configMap = ConfigUtils.getPropertiesJavaMap(KafkaConf.class, "/Kafka.properties");

    public static final String KAFKA_CONFIG_BOOTSTRAP_SERVER_KEY = configMap.get(KafkaConstant.KAFKA_CONFIG_BOOTSTRAP_SERVER_KEY);
    public static final String KAFKA_CONFIG_ZOOKEEPER_CONNECT_KEY = configMap.get(KafkaConstant.KAFKA_CONFIG_ZOOKEEPER_CONNECT_KEY);

    public static final String KAFKA_TOPIC_PV = configMap.get(KafkaConstant.KAFKA_TOPIC_PV);
    public static final String KAFKA_TOPIC_EVENT = configMap.get(KafkaConstant.KAFKA_TOPIC_EVENT);
    public static final String KAFKA_TOPIC_STREAM1 = configMap.get(KafkaConstant.KAFKA_TOPIC_STREAM1);
    public static final String KAFKA_TOPIC_STREAM2 = configMap.get(KafkaConstant.KAFKA_TOPIC_STREAM2);
}

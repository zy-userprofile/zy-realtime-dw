package cn.zy.userprofile.realtime.dw.spark.common.constant;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/24 15:04
 */
public interface KafkaConstant {

    /****** Kafka 配置常量 ******/
    String KAFKA_CONFIG_BOOTSTRAP_SERVER_KEY = "kafka.bootstrap.servers";
    String KAFKA_CONFIG_ZOOKEEPER_CONNECT_KEY = "kafka.zookeeper.connect";

    /****** Kafka Topic ******/
    String KAFKA_TOPIC_PV = "kafka.topic.pv";
    String KAFKA_TOPIC_EVENT = "kafka.topic.event";
    String KAFKA_TOPIC_SESSION = "kafka.topic.session";
    String KAFKA_TOPIC_METRICS = "kafka.topic.metrics";
    String KAFKA_TOPIC_STREAM1 = "kafka.topic.stream1";
    String KAFKA_TOPIC_STREAM2 = "kafka.topic.stream2";
}

package cn.zy.userprofile.realtime.dw.model.test

import java.util.Properties

import cn.zy.userprofile.realtime.dw.common.constants.KafkaConstant
import cn.zy.userprofile.realtime.dw.common.utils.ConfigUtils
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.common.typeinfo.{TypeHint, TypeInformation}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/16 10:51
 *
 * Flink消费Kafka数据 Demo
 */
object KafkaTestApp {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        val kafkaConfigMap = ConfigUtils.getPropertiesMap(this.getClass, "/kafka.properties")

        val properties = new Properties()
        properties.setProperty("bootstrap.servers", kafkaConfigMap(KafkaConstant.KAFKA_CONFIG_BOOTSTRAP_SERVER_KEY))

        val stream = env
            .addSource(new FlinkKafkaConsumer[String]("stream1", new SimpleStringSchema(), properties))
            .print()

        env.execute("Scala KafkaTest Example")
    }

}

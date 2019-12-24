package cn.zy.userprofile.realtime.dw.model.task.event

import java.util.Properties

import cn.zy.userprofile.realtime.dw.common.constants.KafkaConstant
import cn.zy.userprofile.realtime.dw.common.model.CmEventLogMsg
import cn.zy.userprofile.realtime.dw.common.utils.ConfigUtils
import com.google.common.base.Strings
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.api.scala._
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions._

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/16 15:02
 */
object EventLogTask {

    private final val logger = LoggerFactory.getLogger(this.getClass)

    val topics = List("stream1", "stream2")

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.enableCheckpointing(5000)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

        val kafkaConfigMap: Map[String, String] = ConfigUtils.getPropertiesMap(this.getClass, "/kafka.properties")

        val properties: Properties = new Properties()
        properties.setProperty("bootstrap.servers", kafkaConfigMap(KafkaConstant.KAFKA_CONFIG_BOOTSTRAP_SERVER_KEY))

        // 配置数据源--Kafka消息的消费者
        val consumer: FlinkKafkaConsumer[String] = new FlinkKafkaConsumer[String](topics, new SimpleStringSchema(), properties)

        val stream = env
            .addSource(consumer)
            .rebalance
            .filter { msg => !Strings.isNullOrEmpty(msg) }
            .map { x => CmEventLogMsg.parse(x) }

        env.execute("Flink-Stream-cm-EventLogTask")
    }

}

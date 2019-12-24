package cn.zy.userprofile.realtime.dw.model.cm

import java.util.Properties

import cn.zy.userprofile.realtime.dw.common.model.CmPageViewLogMsg
import cn.zy.userprofile.realtime.dw.spark.common.conf.KafkaConf._
import com.google.common.base.Strings
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.slf4j.LoggerFactory

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/24 15:01
 * 用户访问PV
 */
object CmFirstLastPageViewLogTask {
    private final val logger = LoggerFactory.getLogger(this.getClass)

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        val properties: Properties = new Properties()
        properties.setProperty("bootstrap.servers", KAFKA_CONFIG_BOOTSTRAP_SERVER_KEY)

        // 配置数据源--Kafka消息的消费者
        val consumer: FlinkKafkaConsumer[String] = new FlinkKafkaConsumer[String](KAFKA_TOPIC_STREAM1, new SimpleStringSchema(), properties)

        val stream = env
            .addSource(consumer)
            .rebalance
            .filter {msg => !Strings.isNullOrEmpty(msg)}
            .map {x => CmPageViewLogMsg.parse(x)}
//            .filter(msg => msg.isValid())
            .keyBy(_.cookieid)
//            .window()
            .print()

        env.execute("Flink-Stream-cm-FirstLastPageViewLogTask")

    }
}

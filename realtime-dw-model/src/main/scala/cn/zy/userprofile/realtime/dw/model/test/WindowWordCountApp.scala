package cn.zy.userprofile.realtime.dw.model.test

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.api.scala._

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/13 17:58
 *
 * Flink实现流式数据之词频统计
 */
object WindowWordCountApp {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        val text = env.socketTextStream("localhost", 9999)

        val counts = text.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
            .map { (_, 1) }
            .keyBy(0)
            .timeWindow(Time.seconds(5))
            .sum(1)

        counts.print()

        env.execute("Window Stream WordCount")
    }

}

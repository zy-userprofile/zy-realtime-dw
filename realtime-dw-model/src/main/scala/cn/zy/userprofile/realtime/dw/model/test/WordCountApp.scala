package cn.zy.userprofile.realtime.dw.model.test

import cn.zy.userprofile.realtime.dw.common.utils.WordCountData
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/13 17:16
 *
 * Flink词频统计Demo
 */
object WordCountApp {

    def main(args: Array[String]): Unit = {

        val params: ParameterTool = ParameterTool.fromArgs(args)

        val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

        env.getConfig.setGlobalJobParameters(params)
        val text =
            if (params.has("input")) {
                env.readTextFile(params.get("input"))
            } else {
                println("Executing WordCount example with default input data set.")
                println("Use --input to specify file input.")
                env.fromCollection(WordCountData.WORDS)
            }

        val counts = text.flatMap{_.toLowerCase.split("\\W+") filter {_.nonEmpty}}
            .map {(_, 1)}
            .groupBy(0)
            .sum(1)

        if (params.has("output")) {
            counts.writeAsCsv(params.get("output"), "\n", " ")
            env.execute("Scala WordCount Example")
        } else {
            println("Printing result to stdout. Use --output to specify output path.")
            counts.print()
        }
    }

}

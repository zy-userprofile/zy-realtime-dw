package cn.zy.userprofile.service.system.hbase

import java.net.URI

import org.apache.spark.sql.SparkSession
import cn.zy.userprofile.realtime.dw.common.utils.fuctions._
import cn.zy.userprofile.realtime.dw.spark.common.conf.HbaseConf
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.hbase.client.ConnectionFactory
import org.apache.hadoop.hbase.{HBaseConfiguration, KeyValue, TableName}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.{HFileOutputFormat2, LoadIncrementalHFiles}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job

/**
 * Author: hufenggang
 * Email: hufenggang2019@gmail.com
 * Date: 2019/12/23
 * Hive数据导出到Hbase
 */
object HiveToHbase {

    def main(args: Array[String]): Unit = {

        if (args.length < 1) {
            println(
                """
                  |Error: Input parameter is less than 1
                  |Must input one parameter.
                  |""".stripMargin)
            sys.exit(1)
        }
        val dataDate = args(1)
        val hiveTable = ""

        val sparkSession = SparkSession.builder().appName("hiveToHbase")
          .master("local[*]")
          .enableHiveSupport()
          .getOrCreate()
        sparkSession.sparkContext.setLogLevel("warn")

        import sparkSession.sql

        val data = sql(
            s"""
               |select rowKey,id from ${hiveTable}
               |""".stripMargin)

        val dataRdd = data.rdd.flatMap(row => {
            val rowKey = row.getAs[String]("rowKey")
            Array(
                (rowKey, ("cf", "ID", nvl(row.getAs[String]("ID".toLowerCase), ""))),
                (rowKey, ("cf", "DATA_TYPE", nvl(row.getAs[String]("ID".toLowerCase), ""))),
                (rowKey, ("cf", "DEVICE_ID", nvl(row.getAs[String]("ID".toLowerCase), ""))))
        })

        val rdds = dataRdd.filter(x => x._1 != null).
          sortBy(x => (x._1, x._2._1, x._2._2))
          .map(x => {
              val rowKey = Bytes.toBytes(x._1)
              val family = Bytes.toBytes(x._2._1)
              val colum = Bytes.toBytes(x._2._2)
              val value = Bytes.toBytes(x._2._3)
              (new ImmutableBytesWritable(rowKey), new KeyValue(rowKey, family, colum, value))
          })

        val tmpDir = "/tmp/test"
        val hconf = new Configuration()
        hconf.set("", "")

        val fs = FileSystem.get(new URI(""), hconf, "hadoop")

        if (fs.exists(new Path(tmpDir))) {
            print("Delete tmp dir and file.")
            fs.delete(new Path(tmpDir), true)
        }

        // 创建HBase的配置
        val conf = HBaseConfiguration.create()
        conf.set("hbase.zookeeper.quorum", HbaseConf.HBASE_ZOOKEEPER_QUORUM)
        conf.set("hbase.zookeeper.property.clientPort", HbaseConf.HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT)
        conf.setInt("hbase.mapreduce.bulkload.max.hfiles.perRegion.perFamily", 5000)

        rdds.saveAsNewAPIHadoopFile(tmpDir,
            classOf[ImmutableBytesWritable],
            classOf[KeyValue],
            classOf[HFileOutputFormat2],
            conf
        )

        val load = new LoadIncrementalHFiles(conf)

        val tableName = ""
        val conn = ConnectionFactory.createConnection(conf)
        val table = conn.getTable(TableName.valueOf(tableName))

        try {
            //获取hbase表的region分布
            val regionLocator = conn.getRegionLocator(TableName.valueOf(tableName))

            //创建一个hadoop的mapreduce的job
            val job = Job.getInstance(conf)

            //设置job名称，随便起一个就行
            job.setJobName("test")

            //此处最重要,需要设置文件输出的key,因为我们要生成HFil,所以outkey要用ImmutableBytesWritable
            job.setMapOutputKeyClass(classOf[ImmutableBytesWritable])

            //输出文件的内容KeyValue
            job.setMapOutputValueClass(classOf[KeyValue])

            //配置HFileOutputFormat2的信息
            HFileOutputFormat2.configureIncrementalLoad(job, table, regionLocator)

            //开始导入
            load.doBulkLoad(new Path(tmpDir), conn.getAdmin, table, regionLocator)
        } catch {
            case ex: Exception => {
                println("Exception!")
            }
        } finally {
            table.close()
            conn.close()
        }

        sparkSession.stop()
    }

}

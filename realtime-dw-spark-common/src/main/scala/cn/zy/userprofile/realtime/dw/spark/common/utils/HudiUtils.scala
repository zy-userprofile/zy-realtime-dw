package cn.zy.userprofile.realtime.dw.spark.common.utils

import org.apache.spark.sql._
import org.apache.spark.sql.SaveMode._
import org.apache.hudi.QuickstartUtils._
import org.apache.hudi.DataSourceReadOptions._
import org.apache.hudi.DataSourceWriteOptions._
import org.apache.hudi.config.HoodieWriteConfig._

/**
 * Author: hufenggang
 * Email: hufenggang2019@gmail.com
 * Date: 2019/12/20
 *
 * Spark读写Hudi工具类实现
 */
object HudiUtils {

    private final val SPARK_SOURCE_FORMAT = "org.apache.hudi"

    /**
     * 插入数据
     *
     * @param df        DataFrame对象
     * @param tableName Hudi表名
     * @param basePath  基本路径
     */
    def insertDF(df: DataFrame, tableName: String, basePath: String): Unit = {
        df.write.format(SPARK_SOURCE_FORMAT).
          options(getQuickstartWriteConfigs).
          option(PRECOMBINE_FIELD_OPT_KEY, "ts").
          option(RECORDKEY_FIELD_OPT_KEY, "uuid").
          option(PARTITIONPATH_FIELD_OPT_KEY, "partitionpath").
          option(TABLE_NAME, tableName).
          mode(Overwrite).
          save(basePath)
    }

    /**
     * 查询数据
     *
     * @param sparkSession SparkSession对象
     * @param basePath     基本路径
     * @return
     */
    def queryDF(sparkSession: SparkSession, basePath: String): DataFrame = {
        val df = sparkSession.
          read.
          format(SPARK_SOURCE_FORMAT).
          load(basePath + "/*/*/*/*")
        df
    }

    /**
     * 更新数据
     *
     * @param df        DataFrame对象
     * @param tableName Hudi表名
     * @param basePath  基本路径
     */
    def updateDF(df: DataFrame, tableName: String, basePath: String): Unit = {
        df.write.format(SPARK_SOURCE_FORMAT).
          options(getQuickstartWriteConfigs).
          option(PRECOMBINE_FIELD_OPT_KEY, "ts").
          option(RECORDKEY_FIELD_OPT_KEY, "uuid").
          option(PARTITIONPATH_FIELD_OPT_KEY, "partitionpath").
          option(TABLE_NAME, tableName).
          mode(Append).
          save(basePath)
    }

    /**
     * 增量查询
     * 获取给定提交时间以来已更改的记录流
     *
     * @param sparkSession SparkSession对象
     * @param basePath     基本路径
     * @param beginTime    开始时间
     * @return
     */
    def queryDFIncrementalQuery(sparkSession: SparkSession, basePath: String, beginTime: String): DataFrame = {
        val df = sparkSession.
          read.
          format(SPARK_SOURCE_FORMAT).
          option(VIEW_TYPE_OPT_KEY, VIEW_TYPE_INCREMENTAL_OPT_VAL).
          option(BEGIN_INSTANTTIME_OPT_KEY, beginTime).
          load(basePath + "/*/*/*/*")
        df
    }

    /**
     * 特定时间段查询
     * 获取给定时间段内已更改的记录流
     *
     * @param sparkSession SparkSession对象
     * @param basePath     基本路径
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @return
     */
    def queryDFPointInTime(sparkSession: SparkSession, basePath: String, beginTime: String, endTime: String) = {
        val df = sparkSession.
          read.
          format(SPARK_SOURCE_FORMAT).
          option(VIEW_TYPE_OPT_KEY, VIEW_TYPE_INCREMENTAL_OPT_VAL).
          option(BEGIN_INSTANTTIME_OPT_KEY, beginTime).
          option(END_INSTANTTIME_OPT_KEY, endTime).
          load(basePath)
        df
    }

}

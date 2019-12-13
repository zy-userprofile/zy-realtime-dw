package cn.zy.userprofile.realtime.dw.common.utils

import collection.JavaConversions._
import java.io.{BufferedInputStream, InputStream}
import java.util.Properties


/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/13 14:46
 *
 * 配置文件加载工具类 -- Scala代码实现
 */
object ConfigScalaUtils {

    def getPropertiesMap(clazz: Class[_], path: String): Map[String, String] = {

        val propertiesMap = scala.collection.mutable.HashMap.empty[String, String]
        val properties: Properties = new Properties
        val in: InputStream = clazz.getResourceAsStream(path)
        properties.load(new BufferedInputStream(in))

        for (entry <- properties.entrySet()) {
            val key = entry.getKey.asInstanceOf[String]
            val value = entry.getValue.asInstanceOf[String]

            propertiesMap += (key -> value)
        }

        propertiesMap.toMap
    }
}

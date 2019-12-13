package cn.zy.userprofile.realtime.dw.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/13 14:35
 *
 * 加载配置文件工具类
 */
public class Property {

    private final static String CONF_NAME = "config-test.properties";

    private static Properties contextProperties;

    static {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONF_NAME);
        contextProperties = new Properties();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
            contextProperties.load(inputStreamReader);
        } catch (IOException e) {
            System.err.println(">>>flink-2-hbase<<<资源文件加载失败!");
            e.printStackTrace();
        }
        System.out.println(">>>flink-2-hbase<<<资源文件加载成功");
    }

    public static String getStrValue(String key) {
        return contextProperties.getProperty(key);
    }

    public static int getIntValue(String key) {
        String strValue = getStrValue(key);
        // 注意，此处没有做校验，暂且认为不会出错
        return Integer.parseInt(strValue);
    }

    public static Properties getKafkaProperties(String groupId) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", getStrValue("kafka.bootstrap.servers"));
        properties.setProperty("zookeeper.connect", getStrValue("kafka.zookeeper.connect"));
        properties.setProperty("group.id", groupId);
        return properties;
    }

}

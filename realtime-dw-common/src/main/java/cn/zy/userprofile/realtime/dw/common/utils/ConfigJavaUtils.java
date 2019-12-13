package cn.zy.userprofile.realtime.dw.common.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/13 14:47
 *
 * 配置文件加载工具类 -- Java代码实现
 */
public class ConfigJavaUtils {

    public static Map<String, String> getPropertiesMap(Class clazz, String path) {
        Map<String, String> propertiesMap = new HashMap<>();

        Properties properties = new Properties();
        InputStream in = clazz.getResourceAsStream(path);

        try {
            properties.load(new BufferedInputStream(in));

            Set<Map.Entry<Object, Object>> entries = properties.entrySet();
            for (Map.Entry<Object, Object> entry : entries) {
                String key = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                propertiesMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertiesMap;
    }
}

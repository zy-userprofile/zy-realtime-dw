package cn.zy.userprofile.realtime.dw.common.utils;


import org.junit.Test;

import java.util.Map;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/13 14:56
 */
public class ConfigJavaUtilsTest {

    @Test
    public void getConfig() {
        Map<String, String> configMap = ConfigJavaUtils.getPropertiesMap(ConfigJavaUtilsTest.class, "/config-test.properties");
        System.out.println(configMap.get("test"));
    }
}

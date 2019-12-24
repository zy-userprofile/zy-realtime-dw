package cn.zy.userprofile.realtime.dw.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/24 9:40
 */
public class JavaUtils {
    private static final Logger logger = LoggerFactory.getLogger(JavaUtils.class);

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            logger.error("IOException should not have been thrown.", e);
        }
    }
}

package cn.zy.userprofile.realtime.dw.common.utils.base;


import com.google.common.base.Strings;

/**
 * 字符串工具类
 */
public class StringUtils {


    /********************** 字符串和数字之间转换 **********************/

    /**
     * 字符串转Integer类型
     *
     * @param value
     * @return
     */
    public static Integer parseInt(String value) {

        if (Strings.isNullOrEmpty(value)) {
            return 0;
        }

        if (!RegexUtils.isWholeNumber(value)) {
            return 0;
        }

        Integer result = 0;

        try {

            result = Integer.parseInt(value);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    /**
     * 字符串转Long类型
     *
     * @param value
     * @return
     */
    public static Long parseLong(String value) {

        if (Strings.isNullOrEmpty(value)) {
            return 0L;
        }

        if (!RegexUtils.isWholeNumber(value)) {
            return 0L;
        }

        Long result = 0L;

        try {
            result = Long.parseLong(value);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    /**
     * 字符串转Double类型
     *
     * @param value
     * @return
     */
    public static Double parseDouble(String value) {

        if (Strings.isNullOrEmpty(value)) {
            return 0.0;
        }

        if (!RegexUtils.isRealNumber(value)) {
            return 0.0;
        }

        Double result = 0.0;

        try {
            result = Double.parseDouble(value);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


}

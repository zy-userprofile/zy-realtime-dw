package cn.zy.userprofile.realtime.dw.common.utils.base;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/13 14:46
 *
 * 正则匹配工具类
 */
public class RegexUtils {


    /**
     * 判断正则匹配是否成功
     *
     * @param regex     正则表达式
     * @param value   值
     * @return
     */
    private static boolean isMatch(String regex, String value) {

        if (Strings.isNullOrEmpty(value)) {
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(value);
        return isNum.matches();
    }

    /**
     * 判断是否是正整数
     *
     * @param value   值
     * @return
     */
    public static boolean isPositiveInteger(String value) {

        if (Strings.isNullOrEmpty(value)) {
            return false;
        }

        return isMatch("^\\+{0,1}[1-9]\\d*", value);
    }


    /**
     * 判断是否是负整数
     *
     * @param value   值
     * @return
     */
    public static boolean isNegativeInteger(String value) {

        if (Strings.isNullOrEmpty(value)) {
            return false;
        }

        return isMatch("^-[1-9]\\d*", value);
    }

    /**
     * 判断是否是正整数或负整数
     *
     * @param orginal   值
     * @return
     */
    public static boolean isInteger(String orginal) {

        if (Strings.isNullOrEmpty(orginal)) {
            return false;
        }

        return isMatch("^\\+{0,1}[1-9]\\d*", orginal) || isMatch("^-[1-9]\\d*", orginal);
    }


    /**
     * 判断是否是整数
     *
     * @param orginal   值
     * @return
     */
    public static boolean isWholeNumber(String orginal) {

        if (Strings.isNullOrEmpty(orginal)) {
            return false;
        }

        return isMatch("[+-]{0,1}0", orginal) || isMatch("^\\+{0,1}[1-9]\\d*", orginal) || isMatch("^-[1-9]\\d*", orginal);
    }

    /**
     * 判断是否是小数
     *
     * @param orginal   值
     * @return
     */
    public static boolean isPositiveDecimal(String orginal) {

        if (Strings.isNullOrEmpty(orginal)) {
            return false;
        }

        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 判断是否是负小数
     *
     * @param orginal   值
     * @return
     */
    public static boolean isNegativeDecimal(String orginal) {

        if (Strings.isNullOrEmpty(orginal)) {
            return false;
        }

        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 判断是否是小数
     *
     * @param orginal   值
     * @return
     */
    public static boolean isDecimal(String orginal) {

        if (Strings.isNullOrEmpty(orginal)) {
            return false;
        }

        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    /**
     * 判断是否是数字类型（包括整数和小数）
     *
     * @param orginal   值
     * @return
     */
    public static boolean isRealNumber(String orginal) {

        if (Strings.isNullOrEmpty(orginal)) {
            return false;
        }

        return isMatch("[+-]{0,1}0", orginal) || isMatch("^\\+{0,1}[1-9]\\d*", orginal) || isMatch("^-[1-9]\\d*", orginal) || isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }
}

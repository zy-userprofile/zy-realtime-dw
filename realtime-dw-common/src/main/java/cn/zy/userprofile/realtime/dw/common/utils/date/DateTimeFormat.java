package cn.zy.userprofile.realtime.dw.common.utils.date;

/**
 * 时间格式枚举类
 */
public enum DateTimeFormat {
    DATE_FORMAT("yyyyMMdd"),
    DATE_SIGN_FORMAT("yyyy-MM-dd"),
    TIME_FORMAT("yyyyMMdd HH:mm:ss"),
    TIME_SIGN_FORMAT("yyyy-MM-dd HH:mm:ss");

    private final String value;

    DateTimeFormat(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}

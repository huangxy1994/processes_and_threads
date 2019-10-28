package com.example.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {

    /**
     * 获取当前格式化时间
     *
     * @return 格式化时间
     */
    public static String getFormatTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String formatStr = formatter.format(new Date());
        return formatStr;
    }

    /**
     * 获取当前格式化时间，用作截图和报告的名称
     *
     * @return 格式化时间
     */
    public static String getReportTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("y-M-d-H-m-s");
        String formatStr = formatter.format(new Date());
        return formatStr;
    }

    /**
     * 将指定时间戳转换成格式化时间
     *
     * @param timeStamp 指定时间戳
     * @return 格式化时间 yyyy-MM-dd HH:mm:ss:SSS
     */
    public static String getFormatTime(long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String formatStr = formatter.format(timeStamp);
        return formatStr;
    }

    /**
     * 将时间戳转换成时分秒
     *
     * @param ms 时间戳
     * @return 时分秒
     */
    public static String getHMS(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(ms);
        return hms;
    }

}

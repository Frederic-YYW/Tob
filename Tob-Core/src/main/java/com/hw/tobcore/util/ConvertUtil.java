package com.hw.tobcore.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class ConvertUtil {

    /**
     * 将对象转化为long型
     *@param obj
     *@param defaultValue
     *@return
     */
    public static long asLong(Object obj, long defaultValue) {
        if(obj == null) {
            return defaultValue;
        }
        if(obj instanceof Number) {
            return ((Number)obj).longValue();
        }
        try {
            return Long.parseLong(String.valueOf(obj));
        } catch(Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将对象转化为int型
     *@param obj
     *@param defaultValue
     *@return
     */
    public static int asInt(Object obj, int defaultValue) {
        if(obj == null) {
            return defaultValue;
        }
        if(obj instanceof Number) {
            return ((Number)obj).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(obj));
        } catch(Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将对象转化为double型
     *@param obj
     *@param defaultValue
     *@return
     */
    public static double asDouble(Object obj, double defaultValue) {
        if(obj == null) {
            return defaultValue;
        }
        if(obj instanceof Number) {
            return ((Number)obj).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(obj));
        } catch(Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将对象转化为float型
     *@param obj
     *@param defaultValue
     *@return
     */
    public static float asFloat(Object obj, float defaultValue) {
        if(obj == null) {
            return defaultValue;
        }
        if(obj instanceof Number) {
            return ((Number)obj).floatValue();
        }
        try {
            return Float.parseFloat(String.valueOf(obj));
        } catch(Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将对象转化为String型
     *@param obj
     *@param defaultValue
     *@return
     */
    public static String asString(Object obj, String defaultValue) {
        if(obj == null) {
            return defaultValue;
        }
        return obj.toString();
    }

    /**
     * 根据str内容转换成Date
     * @param dateStr 时间字符串
     * @return sql date
     * @throws ParseException
     */
    public static Timestamp asSqlTimestamp(String dateStr) throws ParseException {
        SimpleDateFormat format;
        int len = dateStr.trim().length();

        if (dateStr.contains("-") && len > 10) {
            if (len == DEFAULT_DATE_FORMAT.length()) {
                format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            }else if (len > DEFAULT_DATE_FORMAT.length()) {
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            }else {
                format = new SimpleDateFormat("yyyy-MM-dd");
            }
            java.util.Date date = format.parse(dateStr.trim());
            return new Timestamp(date.getTime());
        } else if (dateStr.contains("年")) {
            format = new SimpleDateFormat("yyyy年MM月dd日");
            java.util.Date date = format.parse(dateStr.trim());
            return new Timestamp(date.getTime());
        } else if (dateStr.contains("/")) {
            java.util.Date date;
            if (len > 10) {
                format  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                date = format.parse(dateStr.trim());
            }else{
                format  = new SimpleDateFormat("yyyy/MM/dd");
                date = format.parse(dateStr.trim());
            }
            return new Timestamp(date.getTime());
        }
        return null;
    }

    /**
     * 根据str内容转换成Date
     * @param dateStr 时间字符串
     * @return sql date
     * @throws ParseException
     */
    public static Date asSqlDate(String dateStr) throws ParseException {
        SimpleDateFormat format;
        int len = dateStr.trim().length();
        if (dateStr.contains("-") && len > 10) {
            if (len == DEFAULT_DATE_FORMAT.length()) {
                format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            }else if (len > DEFAULT_DATE_FORMAT.length()) {
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            }else {
                format = new SimpleDateFormat("yyyy-MM-dd");
            }
            java.util.Date date = format.parse(dateStr.trim());
            return new Date(date.getTime());
        } else if (dateStr.contains("年")) {
            format = new SimpleDateFormat("yyyy年MM月dd日");
            java.util.Date date = format.parse(dateStr.trim());
            date = DateUtil.strToDate(DateUtil.formatDate(date, "yyyy-MM-dd"), "yyyy-MM-dd");
            return new Date(date.getTime());
        } else if (dateStr.contains("/")) {
            java.util.Date date;
            if (len > 10) {
                format  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                date = format.parse(dateStr.trim());
                date = DateUtil.strToDate(DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
            }else{
                format  = new SimpleDateFormat("yyyy/MM/dd");
                date = format.parse(dateStr.trim());
                date = DateUtil.strToDate(DateUtil.formatDate(date, "yyyy-MM-dd"), "yyyy-MM-dd");
            }
            return new Date(date.getTime());
        }
        return null;
    }

    public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

}

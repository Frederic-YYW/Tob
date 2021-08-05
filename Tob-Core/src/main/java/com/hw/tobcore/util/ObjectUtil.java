package com.hw.tobcore.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ObjectUtil extends StringUtils {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 为空的     * @param obj     * @return
     */
    public static boolean empty(Object obj) {
        if (obj == null) {
            return true;
        }
//        if (obj instanceof Number) {
//            return BigDecimal.valueOf(((Number) obj).doubleValue()).compareTo(BigDecimal.valueOf(0.0)) == 0;
//        }
        if (obj instanceof CharSequence) {
            return StringUtils.isBlank(obj.toString());
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return CollectionUtils.isEmpty((Collection) obj);
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (Objects.equals("{}", JSON.toJSON(obj))){
            return true;
        }
        return false;
    }

    /**
     * 不为空的     * @param obj     * @return
     */
    public static boolean notEmpty(Object obj) {
        return !empty(obj);
    }

    public static boolean equals(Object arg1, Object arg2) {
        return ObjectUtils.nullSafeEquals(arg1, arg2);
    }

    public static int hashCode(Object obj) {
        return ObjectUtils.nullSafeHashCode(obj);
    }

    public static boolean like(String source, String str) {
        return source.indexOf(str) >= 0;
    }

    public static String toString(Object obj) {
        return Objects.toString(obj, "");
    }

    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("转换字符串失败:'{}'");
            return "";
        }
    }
}

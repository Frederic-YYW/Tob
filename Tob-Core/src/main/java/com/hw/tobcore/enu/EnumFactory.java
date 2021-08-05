package com.hw.tobcore.enu;

import java.util.EnumSet;

/**
 * @Description: ${枚举工厂类}
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2019-09-25 14:23
 */
public class EnumFactory {
    public static <T extends Enum<T> & BaseEnum<V, I>, V, I> T getByCode(Class<T> clazz, V code) {
        EnumSet<T> set = EnumSet.allOf(clazz);
        for (T t : set) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        return null;
    }

    public static <T extends Enum<T> & BaseEnum<V, I>, V, I> T getByMsg(Class<T> clazz, I msg) {
        EnumSet<T> set = EnumSet.allOf(clazz);
        for (T t : set) {
            if (t.getMsg().equals(msg)) {
                return t;
            }
        }
        return null;
    }

}

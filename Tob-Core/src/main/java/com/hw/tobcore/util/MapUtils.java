package com.hw.tobcore.util;

import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtils {
    /**
     * 将Map<String, String[]>转为Map<String, String>
     *
     * @param dest
     * @param src
     */
    public static void populate(Map<String, String> dest, Map<String, String[]> src) {
        Iterator<Entry<String, String[]>> it = src.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String[]> e = it.next();
            dest.put(e.getKey(), e.getValue()[0]);
        }
    }

    /**
     * 将a=2&b=3这种String转换为map
     *
     * @param q
     * @return
     */
    public static Map<String, Object> string2Map(String q) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (q != null && q.length() > 0) {
            String[] pairs = q.split("&");
            for (String s : pairs) {
                String[] kv = s.split("=");
                if (kv != null && kv.length == 2) {
                    params.put(kv[0], kv[1]);
                } else {
                    params.put(kv[0], null);
                }
            }
        }
        return params;
    }

    /**
     * 实体对象转成Map     * @param obj 实体对象     * @return
     */
    public static Map<String, Object> ObjectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * Map转成实体对象
     * @param map map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static Object MapToObject(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}

package com.hw.tobcore.util;

import java.io.*;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/8/21 13:39
 */
public class CloneUtils {
    public static <T>T deepClone(Object obj,Class<T> clzz ) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ByteArrayInputStream iso = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);

            iso = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(iso);
            T sheep = (T) ois.readObject();
            return sheep;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                bos.close();
                ois.close();
                iso.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

package com.hw.tobcore.util;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/12/29 16:49
 */
public class CardUtils {

    //身份证、临时居民身份证、户口簿
    public static boolean isCardno(String cardType) {
        if ("111".equals(cardType) || "112".equals(cardType) || "113".equals(cardType)) {
            return true;
        }
        return false;
    }
}

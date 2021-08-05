package com.hw.tobcore.util;

public class RandomUtil {

    public static int random6() {
        return (int)((Math.random()*9+1)*100000);
    }

    public static int random8() {
        return (int)((Math.random()*9+1)*10000000);
    }
}

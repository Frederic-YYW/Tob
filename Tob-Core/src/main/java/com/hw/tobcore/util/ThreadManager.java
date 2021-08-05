package com.hw.tobcore.util;

import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/8/3 11:05
 */
public class ThreadManager {

    public static void exectue(Runnable runnable) {
        Executors.newCachedThreadPool().execute(runnable);
    }
}

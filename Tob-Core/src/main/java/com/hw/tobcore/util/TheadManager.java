package com.hw.tobcore.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Description  : 线程池工管理类
 * Author : Frederic-YYW
 * E-mail : yuanyangwen@iristar.com.cn
 * Date   : 2019/6/13 11:52
 * Version: 1.0
 */
@Slf4j
public class TheadManager {

    private static ThreadPoolExecutor threadPool;
    private static int corePoolSize;
    private static int maximumPoolSize;
    private static int queue;

    static {
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        queue = corePoolSize;
        maximumPoolSize = corePoolSize + queue;
    }

    private TheadManager() {
    }

    /**
     * 获取线程池
     *
     * @return
     */
    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool == null) {
            synchronized (TheadManager.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queue), Executors.defaultThreadFactory(),
                            new ThreadPoolExecutor.DiscardPolicy());
                }
            }
        }
        return threadPool;
    }

    /**
     * 执行任务
     *
     * @param runnable
     */
    public static void execute(Runnable runnable) {
        ThreadPoolExecutor threadPool = getThreadPool();
        try {
            threadPool.execute(runnable);
        } catch (Exception e) {
            log.error("[Threadpool execute runnable error] errMsg={}", e.toString());
        } finally {
            threadPool.shutdown();
        }
    }

    /**
     * 带返回值
     *
     * @param callable
     * @return
     */
    public static Object execute(Callable<Object> callable) {
        ThreadPoolExecutor threadPool = getThreadPool();
        try {
            Future<Object> submit = threadPool.submit(callable);
            if (submit == null) {
                return null;
            }
            return submit.get();
        } catch (Exception e) {
            log.error("[Threadpool execute callable error] errMsg={}", e.toString());
        } finally {
            threadPool.shutdown();
        }
        return null;
    }

    public static Object execute(ThreadPoolExecutor threadPool, Callable<Object> callable) {
        if (threadPool == null) {
            return null;
        }
        try {
            Future<Object> submit = threadPool.submit(callable);
            if (submit == null) {
                return null;
            }
            return submit.get();
        } catch (Exception e) {
            log.error("[Threadpool execute callable error] errMsg={}", e.toString());
        } finally {
            threadPool.shutdown();
        }
        return null;
    }
}

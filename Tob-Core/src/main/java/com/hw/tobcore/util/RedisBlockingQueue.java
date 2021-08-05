package com.hw.tobcore.util;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/6/28 13:50
 */
public class RedisBlockingQueue<T> {

    private Jedis jedis;
    private String queueName;

    public RedisBlockingQueue(Jedis jedis, String queueName) {
        this.jedis = jedis;
        this.queueName = queueName;
    }

    public T take() {
        System.out.println();
        List<String> datas = jedis.brpop(0, queueName);
        System.out.println(Thread.currentThread().getName() + "===>" + datas.get(1));
        return (T) datas.get(1);
    }


    public void push(T msg) {
        jedis.rpush(queueName, msg.toString());
    }

}

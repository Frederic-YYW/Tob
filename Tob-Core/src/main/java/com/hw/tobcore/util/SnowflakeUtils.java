package com.hw.tobcore.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/8/19 13:44
 */
public class SnowflakeUtils {

    /**
     * @param workerId     终端ID
     * @param datacenterId 数据中心ID
     * @return
     */
    public static long getUniqueId(long workerId, long datacenterId) {
        Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }

    public static String getUniqueStrId(long workerId, long datacenterId) {
        Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return String.valueOf(snowflake.nextId());
    }
}

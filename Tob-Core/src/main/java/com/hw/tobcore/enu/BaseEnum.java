package com.hw.tobcore.enu;

/**
 * @Description: ${枚举基类}
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2019-09-17 10:53
 */

public interface BaseEnum<T,I> {
    T getCode();

    I getMsg();
}


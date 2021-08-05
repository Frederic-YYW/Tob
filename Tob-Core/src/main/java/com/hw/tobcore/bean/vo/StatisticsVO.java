package com.hw.tobcore.bean.vo;

import lombok.Data;

/**
 * @Description:统计vo
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/12/10 17:32
 */

@Data
public class StatisticsVO {
    private String name;
    private int total;
    private String code;
    private int upload;
    private int local;
}

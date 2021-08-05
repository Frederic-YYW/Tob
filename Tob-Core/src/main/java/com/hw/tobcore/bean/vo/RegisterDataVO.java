package com.hw.tobcore.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 注册数据
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/4/26 13:38
 */
@Data
public class RegisterDataVO implements Serializable {
    private byte[] RecLeftFeature;
    private byte[] RecRightFeature;
    private byte[] regLeftFeature;
    private byte[] regRightFeature;
    private String id;
}

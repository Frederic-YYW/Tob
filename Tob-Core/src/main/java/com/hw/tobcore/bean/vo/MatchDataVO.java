package com.hw.tobcore.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/4/26 13:38
 */
@Data
public class MatchDataVO implements Serializable {
    private byte[] RecLeftFeature;
    private byte[] RecRightFeature;
    private byte[] LocalRecLeftFeature;
    private byte[] LocalRecRightFeature;
    private byte[] regLeftFeature;
    private byte[] regRightFeature;
    private Integer OperType; //注册0，查验，核查2 OperTypeEnum
    private String leftImg;
    private String rightImg;
    private String id;
    private String irisId; //只有在核查一比一时使用
}

package com.hw.tobcore.bean.vo;

import lombok.Data;

/**
 * @Description:node 节点详情
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/4/29 18:16
 */
@Data
public class NodeDetailsInfo {

    private String cpu;
    private String memory;
    private String diskSpace;
    private String ip;
    private String port;
    private String nodeId;
    private int leftFeatureCount;
    private int rightFeatureCount;
    private int totalFeatureCount;
    private String updateAt;
}

package com.hw.tobcore.bean.vo;

import lombok.Data;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/4/29 10:05
 */
@Data
public class NodeRecoResultVO {
    private String irisId;
    private String resultId;
    private String nodeId;

    public NodeRecoResultVO(String irisId, String resultId, String nodeId) {
        this.irisId = irisId;
        this.resultId = resultId;
        this.nodeId = nodeId;
    }
    public NodeRecoResultVO(String irisId, String resultId) {
        this.irisId = irisId;
        this.resultId = resultId;
        this.nodeId = nodeId;
    }
}

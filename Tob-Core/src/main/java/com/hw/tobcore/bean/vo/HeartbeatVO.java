package com.hw.tobcore.bean.vo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/7/30 13:20
 */
@Data
public class HeartbeatVO {

    // @Pattern(regexp = "([1-9]|[1-9]\\\\d|1\\\\d{2}|2[0-4]\\\\d|25[0-5])(\\\\.(\\\\d|[1-9]\\\\d|1\\\\d{2}|2[0-4]\\\\d|25[0-5])){3}", message = "ip地址错误")
    private String ip;

    @Min(0)
    @Max(65535)
    private Integer port;

    @Min(0)
    @Max(65535)
    private Integer rpcPort;

    @Min(0)
    private Integer regCount;

    @Min(0)
    private Integer recoCount;

    @NotBlank(message = "节点 id 不能为空")
    private String nodeId;

    //@Pattern(regexp = "/^([1-2][0-9][0-9][0-9]-[0-1]{0,1}[0-9]-[0-3]{0,1}[0-9])\\s(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d",message = "时间必须是 yyyy-MM-dd HH:mm:ss")
    private String heartbeatAt;


}

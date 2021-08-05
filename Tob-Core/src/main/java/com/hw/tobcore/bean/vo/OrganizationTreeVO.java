package com.hw.tobcore.bean.vo;

import lombok.Data;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/12/8 18:09
 */
@Data
public class OrganizationTreeVO {

    private String client_id;//请求方ID    请求方在请求服务系统上的唯一标识。必填。    请求方ID需在授权的IP范围内调用。
    private String client_secret;//请求方密钥    请求方ID对应的密钥。必填。
    private String codeType;// 获取的代码类型;//    GAJGJGDM（公安机关机构代码）    RYLBDM（被采集虹膜人员类别代码）
    private String pId;// 父层级代码ID。选填，不填时为获取根层级代码，否则为获取此ID下层的代码值。
}

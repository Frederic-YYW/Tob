package com.hw.tobcore.bean.vo;

import com.iristar.center.entity.base.SecurityConfig;
import lombok.Data;

import java.util.List;

/**
 * @Description:全局安全设置
 * @Author:Frederic-YYW全局安全控制
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/8/17 17:00
 */
@Data
public class SecurityConfigVO {
    private List<String> forbidIP;                      //禁止访问访问ip
    private List<SecurityConfig.ForbidOrg> forbidOrganization;     //禁止访问单位
    private List<SecurityConfig.ForbidUser> forbidUserList;            //禁止访问的机构

    private String allowIPRegion;                       //允许访问ip区段 10.10.1.00-10.10.255.255,"-"连接

    private Integer ipVisMaxCount;                      //ID最大访问次数，天
    private Integer ipVisFrequency;                     //ID最大访问频率，天
    private Integer orgVisMaxCount;                     //单位最大访问次数，天
    private Integer userVisitsCount;                    //用户最大访问次数，天

    private Integer registerModel;                      //采集模式
    private Integer regcognitionModel;                  //识别模式
    private Integer checkModel;                         //核查模式

    private String systemName;                          //系统名称
}

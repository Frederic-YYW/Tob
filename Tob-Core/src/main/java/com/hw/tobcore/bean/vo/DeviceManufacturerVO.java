package com.hw.tobcore.bean.vo;

import lombok.Data;

/**
 * @Description:设备厂商查询实体
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/8/20 18:18
 */
@Data
public class DeviceManufacturerVO {

    private String name;            //设备厂商名称

    private String sbcsdm;          //设备厂商信用代码

    private String client_id;       //请求方ID

    private String client_sectet;   //请求方公钥

    private Boolean enable = null;      //是否禁用

    private int pageNum = 1;            //启始页,从1开始

    private int pageSize = 10;          //每页条数

}

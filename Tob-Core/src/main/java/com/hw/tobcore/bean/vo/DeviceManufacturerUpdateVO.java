package com.hw.tobcore.bean.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @Description:设备厂商实体
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/8/20 18:18
 */
@Data
public class DeviceManufacturerUpdateVO {
    @NotEmpty(message = "数据id不能为空")
    private String id;

    @NotEmpty(message = "设备厂商名称不能为空")
    private String name;            //设备厂商名称

    @Pattern(regexp = "^[1-9A-GY]{1}[1239]{1}[1-5]{1}[0-9]{5}[0-9A-Z]{10}$", message = "设备厂商代码错误")
    private String sbcsdm;          //设备厂商信用代码

    @Length(min = 2, message = "client_id不能少于2位")
    private String client_id;       //请求方ID

    @Length(min = 2, message = "client_sectet不能少于2位")
    private String client_sectet;   //请求方公钥
}

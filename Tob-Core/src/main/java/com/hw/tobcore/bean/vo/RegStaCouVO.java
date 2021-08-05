package com.hw.tobcore.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description:本地注册
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/12/11 13:40
 */
@Data
public class RegStaCouVO {
    private String code;        //按操作员身份证号搜索
    private Integer bdzt;      //本地处理结果
    private Integer sjzt;      //上级处理结果
    private String cjr_gmsfhm;      //采集人身份证号
    private String cjdw;        //采集单位code
    private String sbcsdm;      //设备厂商代码
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startAt;              //开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endAt;                //结束时间
}

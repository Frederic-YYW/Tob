package com.hw.tobcore.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RegisterListVO {

    private Integer status;             //远程
    private Integer localStatus;        //本地状态
    private String user_dept;          //采集机构代码
    private String cjr_gmsfhm;         //操作员身份证号
    private String zjhm;               //被采集人身份证号码
    private String rylb;               //人员类别
    private String xm;                  //被采集人姓名
    private String sbcsdm;             //设备厂商代码
    private String sbbh;                //设备编号
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING,timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startAt;              //开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING,timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endAt;                //结束时间
    private int pageNum = 1;
    private int pageSize = 10;
}

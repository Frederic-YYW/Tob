package com.hw.tobcore.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RegInfoVO {
    private String id;
    @NotBlank(message = "姓名不能为空")
    private String realname;
    @NotNull(message = "性别不能为空")
    private String sex;
    @NotBlank(message = "出生日期不能为空")
    private String birthday;
    private String homeAddress;
    private String email;
    private String phone1;
    private String phone2;
    @NotBlank(message = "人员分类不能为空")
    private String userGroup; //人员类别
    @NotBlank(message = "证件类型不能为空")
    private String cardType;
    @NotBlank(message = "证件号不能为空")
    //@Pattern(regexp = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)", message = "采集人身份证格式不正确")
    private String cardno;
    private String cardImg;
    @NotBlank(message = "民族不能为空")
    private String nation;
    @NotBlank(message = "国家不能为空")
    private String country;
    private String institution;  //数据采集单位
    private String hometown;
    private String message;
    private String expireAt;
    private Integer leftEyeStatus; //左眼情况
    private Integer rightEyeStatus; //右眼情况
    private Integer bothEyeStatus; //双眼情况

    //    @Digits(fraction = 7, integer = 3, message = "采集时间过长")
    private double processTime;  //采集处理时间
    private double leftEyeScore; //左眼评分
    private double rightEyeScore; //右眼评分
    private String leftEyeImg;
    private String rightEyeImg;
    @NotBlank(message = "录入标志不能为空")
    private String regFlag; //录入标志
    //    @NotBlank(message = "采集人姓名不能为空")
    private String regRealname; //采集姓名
    //    @NotBlank(message = "采集人身份证不能为空")
    private String regCardno; //采集人身份证
    private String regTime; //采集时间
    @NotBlank(message = "强制采集标志不能为空")
    private String forceRegFlag; //强制采集标志
    // @JsonProperty("isOnLine")
    @NotNull(message = "远程状态不能为空")
    private Integer offline;
    private String token;
    private Integer status;       //上级状态
    private Integer localStatus;  //本地状态，对应
    private String adminName;
    private String adminInstitution;
    private String upload;
    private String delete;
    private Date createAt;
    private String cjr_xm;
    private String sbcsdm;             //设备厂商代码
    private String sbbh;                //设备编号
    private String version;
}

package com.hw.tobcore.bean.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CheckInfoVO {
    private String id;
    private String sn;
    private String address;
    @NotBlank(message = "姓名不能为空")
    private String realname;
    @NotBlank(message = "性别不能为空")
    private String sex;
//    @NotBlank
//    @Pattern(regexp = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)", message = "出生日期格式必须为'YYYY-MM-dd")
    private String birthday;
    @NotBlank(message = "证件类型不能为空")

    private String cardType;
    @NotBlank(message = "证件号不能为空")
//    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)", message = "采集人身份证格式不正确")
    private String cardno;
    private String cardImg;
    @NotBlank(message = "民族不能为空")
    private String nation;
    @NotBlank(message = "国家不能为空")
    private String country;

    @Min(0)
    @Max(value = 9)
    private Integer leftEyeStatus; //左眼情况

    @Min(0)
    @Max(value = 9)
    private Integer rightEyeStatus; //右眼情况

    @Min(0)
    @Max(value = 3)
    @NotNull(message = "双眼情况不能为空")
    private Integer bothEyeStatus; //双眼情况

//    @Digits(fraction = 7, integer = 3, message = "采集时间过长")
    private double processTime;  //采集处理时间
    private double leftEyeScore; //左眼评分
    private double rightEyeScore; //右眼评分

    private String leftEyeImg;
    private String rightEyeImg;
    @NotBlank(message = "录入标志不能为空")
    private String regFlag; //录入标志
    @NotBlank(message = "采集姓名不能为空")
    private String regRealname; //采集姓名
    @NotBlank(message = "采集身份证不能为空")
//    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)", message = "被采集人身份证格式不正确")
    private String regCardno; //采集身份证
    @NotBlank(message = "强制采集标志不能为空")
    private String forceRegFlag; //强制采集标志

    @NotBlank(message = "证件标志不能为空")
    private String cardFlag;

    @NotNull(message = "远程状态不能为空")
    @JsonProperty("isOnLine")
    private Integer offline;
    private String token;
    private String version;
}

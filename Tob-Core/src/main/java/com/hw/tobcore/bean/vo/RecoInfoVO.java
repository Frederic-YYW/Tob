package com.hw.tobcore.bean.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RecoInfoVO {
    private String id;
    private String sn;
    private String address;
    //    @Digits(fraction = 7, integer = 3, message = "采集时间过长")
    private double processTime;  //采集处理时间
    @Min(0)
    @Max(value = 3)
    @NotNull(message = "双眼情况不能为空")
    private Integer bothEyeStatus; //双眼情况
    private String leftEyeImg;
    private String leftEyeStatus; //左眼情况
    private Integer leftEyeScore; //左眼评分
    private String rightEyeImg;
    private String rightEyeStatus; //右眼情况
    private Integer rightEyeScore; //右眼评分
    private String forceRegFlag; //强制采集标志
    //    @NotBlank(message = "采集姓名不能为空")
    private String regRealname; //采集姓名
    //    @NotBlank(message = "采集身份证不能为空")
//    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)", message = "被采集人身份证格式不正确")
    private String regCardno; //采集身份证
    @JsonProperty("isOnLine")
    private Integer offline;
    private String token;
    private String version;
}

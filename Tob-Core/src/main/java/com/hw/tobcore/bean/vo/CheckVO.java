package com.hw.tobcore.bean.vo;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CheckVO {
    @NotBlank(message = "采集人姓名不能为空")
    private String cjrXm;              //采集人姓名
    @NotBlank(message = "采集人身份证不能为空")
    private String cjrGmsfhm;          //采集人身份证号
    @NotBlank(message = "录入标志不能为空")
    private String lrbz;                //录入标志
    @NotBlank(message = "强制采集标志不能为空")
    private String qzcjbz;              //强制采集标志

    @NotBlank(message = "证件类型不能为空")
    private String zjlx;         //被采集人证件类型代码
    @NotBlank(message = "证件号不能为空")
    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)", message = "采集人身份证格式不正确")
    private String zjhm;           //被采集人证件号码  “999”的，不填写。其他情况
    @NotBlank(message = "被采集人姓名不能为空")
    private String xm;             //被采集人姓名
    @NotBlank(message = "被采集人性别不能为空")
    private String xb;             //被采集人性别
    @NotBlank(message = "被采集人民族不能为空")
    private String mz;             //被采集人民族代码
    @NotBlank(message = "被采集人国家不能为空")
    private String gj;             //被采集人国家代码
    @NotBlank(message = "被采集人出生日期不能为空")
    private String zjzp;                //二代证照片  只有读证录入时才有证件照片 jpg格式
    private Integer zyycjdm;             //左右眼采集代码
    private Integer zyqsqkdm;            //左眼缺失情况代码
    private Integer yyqsqkdm;            //右眼缺失情况代码
    private String hmzpZy;             //虹膜照片左眼  根据左右眼采集代码选择填写 bmp格式
    private String hmzpYy;             //虹膜照片右眼                          bmp格式
    @Digits(fraction = 7, integer = 3, message = "采集时间过长")
    private String cjtphs;              //采集图片耗时
    private String zyXxzlpf;           //左眼虹膜照片信息质量评分
    private String yyXxzlpf;           //右眼虹膜照片信息质量评分
    private Integer offline;           //本地核验、远程核验
}

package com.hw.tobcore.bean.vo;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Data
public class RecognizeVO {
    private String cjrXm;              //采集人姓名
    private String cjrGmsfhm;          //采集人身份证号
    @NotBlank(message = "强制采集标志不能为空")
    private String qzcjbz;              //强制采集标志
    private Integer zyycjdm;             //左右眼采集代码
    private Integer zyqsqkdm;            //左眼缺失情况代码
    private Integer yyqsqkdm;            //右眼缺失情况代码
    private String hmzpZy;             //虹膜照片左眼  根据左右眼采集代码选择填写 bmp格式
    private String hmzpYy;             //虹膜照片右眼                          bmp格式
    @Digits(fraction = 7, integer = 3, message = "采集时间过长")
    private String cjtphs;              //采集图片耗时
    private String zyXxzlpf;           //左眼虹膜照片信息质量评分
    private String yyXxzlpf;           //右眼虹膜照片信息质量评分
    private Integer offline;

}

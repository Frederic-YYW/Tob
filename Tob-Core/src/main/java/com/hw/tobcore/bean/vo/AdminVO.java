package com.hw.tobcore.bean.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AdminVO {
    @NotBlank(message = "用户名不能为空")
    @Length(max = 50,min = 6,message = "账号最少六位字母数字组合")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotNull(message = "用户等级不能为空")
    private Integer level;
    @NotBlank(message = "姓名不能为空")
    private String realname;
    @NotBlank(message = "备注不能为空")
    private String message;
    private String random;
    private int pageNum = 1;
    private int pageSize = 10;
    private Boolean enable;
}

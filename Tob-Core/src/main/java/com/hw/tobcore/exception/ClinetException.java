package com.hw.tobcore.exception;

import com.iristar.center.enums.ResultEnum;

/**
 * @Description: 客户端异常处理类
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2019/12/13 10:29
 */
public class ClinetException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;

    public ClinetException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ClinetException(ResultEnum resultEnum) {
        new ClinetException(resultEnum.getCode(), resultEnum.getMsg());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

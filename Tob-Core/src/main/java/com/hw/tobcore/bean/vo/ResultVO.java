package com.hw.tobcore.bean.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:返回结果通用类
 * @Auther: Frederic-YYW
 * @Date: 19-7-31 17:41
 * @Email:yuanyangwen@iristar.com.cn
 */
@Data
@ToString
public class ResultVO {
    private Integer code;
    private String message;
    private String status;
    private Object content;

    public ResultVO() {

    }

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public ResultVO(Integer code, String msg, Object content) {
        this.code = code;
        this.message = msg;
        this.content = content;
    }
}

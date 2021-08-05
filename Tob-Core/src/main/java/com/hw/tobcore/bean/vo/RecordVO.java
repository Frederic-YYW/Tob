package com.hw.tobcore.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class RecordVO {
    @NotBlank(message = "记录类型不能为空")
    private String opType;
    private String realname;
    private String sex;
    private Integer cardType;
    private String userGroup;
    private Integer status; //远程
    private Integer localStatus;
    private String institution;
    private String adminInstitution;
    private String adminid;
    private List<String> cardList;
    private Date startAt;
    private Date endAt;
    private int pageNum = 1;
    private int pageSize = 10;
}

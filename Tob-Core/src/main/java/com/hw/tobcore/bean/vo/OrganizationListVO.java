package com.hw.tobcore.bean.vo;

import lombok.Data;

/**
 * @Description:存储树形机关信息
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/12/8 17:23
 */
@Data
public class OrganizationListVO {

    private String code;   //机构代码

    private String parentId;   //上级id

    private String level;   //层级

    private String ip;

    private String sn;

    private Boolean enable = null; //是否禁用

    private int pageNum = 0;            //启始页,从0开始

    private int pageSize = 10;          //每页条数
}

package com.hw.tobcore.bean.vo;

import com.iristar.center.entity.base.Admin;
import com.iristar.center.entity.base.Device;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/8/17 14:34
 */
@Data
public class GlobalInstitutionVO implements Serializable {
    private List<Device> deviceList;              //设备list
    private String client_id;                     //请求方ID
    private String client_sectet;                 //请求方公钥
    private String private_key;                   //私钥
    private String deptName;                      //单位名称
    private String deptCode;                      //单位code
    private List<Admin> adminList;                //管理员id集合
    private String institutionId;                 //机构id
    private String parentId;                      //上级机关id

}

package com.hw.tobcore.bean.vo;

import com.iristar.center.entity.base.Organization;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @Description:存储树形机关信息
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/12/8 17:23
 */
@Data
public class OrganizationUpdateVO {
    @NotEmpty(message = "id不能为空")
    private String id;

    @Pattern(regexp = "^\\d{12}$", message = "机构代码错误")
    private String code;

    @NotEmpty(message = "机构名称不能为空")
    private String name; //对应name

    @NotEmpty(message = "父id不能为空")
    private String parentId; //父层级代码ID

    @NotEmpty(message = "父组织名称不能为空")
    private String parentName; //父层级代码ID

    @NotEmpty(message = "机构层级不能为空不能为空")
    private String level;

    private List<String> allowIP;                     //允许访问的ip

    private List<Organization.DeviceInfo> deviceList;              //机构允许访问的设备

}

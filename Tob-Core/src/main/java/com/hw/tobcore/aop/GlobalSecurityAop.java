package com.hw.tobcore.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iristar.center.cv.tools.ImageUtil;
import com.iristar.center.entity.base.DeviceManufacturer;
import com.iristar.center.entity.base.Organization;
import com.iristar.center.entity.base.SecurityConfig;
import com.iristar.center.enums.Data;
import com.iristar.center.enums.DataEntrySystemModeEnum;
import com.iristar.center.enums.Iris;
import com.iristar.center.enums.RemoteResultEnum;
import com.iristar.center.service.DeviceManufacturerService;
import com.iristar.center.service.OrganizationService;
import com.iristar.center.service.SecurityConfigService;
import com.iristar.center.util.JsonUtils;
import com.iristar.center.util.RemoteResultUtils;
import com.iristar.center.util.SpringContextUtils;
import com.iristar.center.util.WebToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:全局安全控制
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/8/17 17:56
 */
@Component
@Aspect
@Slf4j
public class GlobalSecurityAop implements CommandLineRunner {
    @Autowired
    private HttpServletRequest request;
    @Value("${remote.image.format}")
    private String regImgFormat;

    /**
     * 注册、查验、核查监控表达式
     */
    private final String POINT_CUT =
            "(execution(public * com.iristar.center.api.v2.IrisCheckApiV2.irisCheck(..)))"
                    + " || (execution(public * com.iristar.center.api.v3.IrisCheckApiV3.irisCheck(..)))"
                    + " || (execution(public * com.iristar.center.api.v2.IrisRecognitionApiV2.inspection(..)))"
                    + " || (execution(public * com.iristar.center.api.v3.IrisRecognitionApiV3.inspection(..)))"
                    + " || (execution(public * com.iristar.center.api.v2.IrisRegisterApiV2.regIris(..)))"
                    + " || (execution(public * com.iristar.center.api.v3.IrisRegisterApiV3.regIris(..)))";

    /**
     * 组织变监控表达式
     */
    private final String ORG_POINT_CUT =
            "(execution(public * com.iristar.center.api.v1.OrganizationApiV1.update(..)))"
                    + " || (execution(public * com.iristar.center.api.v1.OrganizationApiV1.create(..)))"
                    + " || (execution(public * com.iristar.center.api.v1.OrganizationApiV1.delete(..)))";
    /**
     * 设备厂商变化切监控表达式
     */
    private final String DEV_MANU_POINT_CUT =
            "(execution(public * com.iristar.center.api.v1.DeviceManufacturerApiV1.update(..)))"
                    + " || (execution(public * com.iristar.center.api.v1.DeviceManufacturerApiV1.create(..)))"
                    + " || (execution(public * com.iristar.center.api.v1.DeviceManufacturerApiV1.forbidden(..)))"
                    + " || (execution(public * com.iristar.center.api.v1.DeviceManufacturerApiV1.delete(..)))";
    /**
     * 全局安全控制变化切监控表达式
     */
    private final String SECURITY_POINT_CUT =
            "(execution(public * com.iristar.center.api.v1.SecurityConfigApiV1.update(..)))"
                    + " || (execution(public * com.iristar.center.api.v1.SecurityConfigApiV1.create(..)))";

    private static Map<String, Object> organizationMap = new HashMap<>();
    private static Map<String, Object> manufacturerMap = new HashMap<>();
    private static Map<String, Object> manufacturerIdMap = new HashMap<>();
    private static Map<String, Object> forbManufacturerMap = new HashMap<>();
    private static Map<String, SecurityConfig.ForbidUser> forbUserMap = new HashMap<>();
    private static Map<String, SecurityConfig.ForbidOrg> forbOrgaMap = new HashMap<>();
    private static List<String> forbidIP = new ArrayList<>();
    private Integer registerModel;
    private Integer regcognitionModel;
    private Integer checkModel;


    /**
     * 对外接口检查
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around(POINT_CUT)
    public Object checkSecurity(ProceedingJoinPoint point) throws Throwable {

        String localIP = WebToolUtils.getIpAddress(request);
        Object[] args = point.getArgs();
        Object obj = args[0];
        JSONObject json = JSON.parseObject(JsonUtils.objectToJson(obj));
        String deptCode = json.getString("user_dept");

        //1、全局安全检查

        //IP禁止访问
        if (forbidIP.contains(localIP)) {
            log.error("[checkSecurity] localIP is forbid visitor ! localIP={} forbidIP={}", localIP, forbidIP);
            return RemoteResultUtils.error(RemoteResultEnum.IP_JZFW, json.getString("request_id"), "3.0");
        }

        //用户是否禁止访问
        String cjrXm = json.getString("cjr_xm");
        String cjrGmsfhm = json.getString("cjr_gmsfhm");
        if (StrUtil.isEmpty(cjrXm)) {
            log.error("[checkSecurity] cjrXm is empty ");
            return RemoteResultUtils.error(RemoteResultEnum.TY_QTCW.getCode(), "采集人姓名不能为空", json.getString("request_id"), "3.0");
        }

        if (StrUtil.isEmpty(cjrGmsfhm)) {
            log.error("[checkSecurity] cjrGmsfhm is empty ");
            return RemoteResultUtils.error(RemoteResultEnum.TY_QTCW.getCode(), "采集人公民身份证号不能为空", json.getString("request_id"), "3.0");
        }

        //用户禁止访问
        if (forbUserMap.containsKey(cjrGmsfhm)) {
            log.error("[checkSecurity] cjrXm,cjrGmsfhm is error ! cjrXm={} cjrGmsfhm={} forbUserMap={}", cjrXm, cjrGmsfhm, forbUserMap);
            return RemoteResultUtils.error(RemoteResultEnum.YH_JZFW, json.getString("request_id"), "3.0");
        }

        //机构禁止访问
        if (forbOrgaMap.containsKey(deptCode)) {
            log.error("[checkSecurity] dept  is forbidden ! deptCode={} forbOrgaMap={}", deptCode, forbOrgaMap);
            return RemoteResultUtils.error(RemoteResultEnum.DW_JZFW, json.getString("request_id"), "3.0");
        }

        //2、机构检查
        //机构代码是否正确
        if (!organizationMap.containsKey(deptCode)) {
            log.error("[ checkSecurity ] organization is forbidden ! deptCode={} organizationMap={}", deptCode, organizationMap);
            return RemoteResultUtils.error(RemoteResultEnum.DW_JZFW.getCode(), "机构未授权", json.getString("request_id"), "3.0");
        }
        Organization organization = (Organization) organizationMap.get(deptCode);

        //机构是否被禁止 废弃
//        if (organization.isEnable()) {
//            log.error("[checkSecurity] organization is not exist ! deptCode={}", deptCode);
//            return RemoteResultUtils.error(RemoteResultEnum.DW_JZFW.getCode(), RemoteResultEnum.DW_JZFW.getMsg(),  new HashMap<>(1), "0.0", json.getString("request_id"), "3.0");
//        }

        //IP是否是全局禁止访问ip
        //IP是否授权
        List<String> ipList = organization.getAllowIP();
        if (CollUtil.isEmpty(ipList) || !ipList.contains(localIP)) {
            log.error("[checkSecurity] Ip  is not authorized  ! localIP={} ipList={}", localIP, ipList);
            return RemoteResultUtils.error(RemoteResultEnum.ID_IP_SQBF.getCode(), "IP 未授权", json.getString("request_id"), "3.0");
        }

        //设备是否授权
        List<Organization.DeviceInfo> deviceList = organization.getDeviceList();
        if (CollUtil.isEmpty(deviceList)) {
            log.error("[checkSecurity] deviceList is empty !");
            return RemoteResultUtils.error(RemoteResultEnum.TY_QTCW.getCode(), "设备未授权", json.getString("request_id"), "3.0");
        }

        //设备是否授权
        String sbbh = json.getString("sbbh");
        Map<String, Organization.DeviceInfo> snMap = deviceList.stream().collect(Collectors.toMap(Organization.DeviceInfo::getSn, a -> a, (k1, k2) -> k1));
        if (StrUtil.isEmpty(sbbh) || !snMap.containsKey(sbbh)) {
            log.error("[checkSecurity] sbbh is not exist !  sbbh={} snMap={}", sbbh, snMap);
            return RemoteResultUtils.error(RemoteResultEnum.TY_QTCW.getCode(), "设备未授权", json.getString("request_id"), "3.0");
        }

        Organization.DeviceInfo deviceInfo = snMap.get(sbbh);
        String code = deviceInfo.getCode();
        String hmcjsbxhdm = json.getString("hmcjsbxhdm");
        if (!code.equals(hmcjsbxhdm)) {
            log.error("[checkSecurity] hmcjsbxhdm is not exist ! hmcjsbxhdm= {} code= {}", hmcjsbxhdm, code);
            return RemoteResultUtils.error(RemoteResultEnum.TY_QTCW.getCode(), "设备类型与授权设备不匹配", json.getString("request_id"), "3.0");
        }

        //3、设备厂商验证
        if (!manufacturerMap.containsKey(deviceInfo.getDevManufacturerId())) {
            log.error("[checkSecurity] manufacturerI is not exist ! manufacturerMap={} deviceInfo={}", manufacturerMap, deviceInfo);
            return RemoteResultUtils.error(RemoteResultEnum.SBCS_DM_CW.getCode(), "设备厂商未授权", json.getString("request_id"), "3.0");
        }

        String sbcsdm = json.getString("sbcsdm");
        if (StrUtil.isEmpty(sbcsdm)) {
            log.error("[checkSecurity] sbcsdm is empty ! sbcsdm= {}", sbcsdm);
            return RemoteResultUtils.error(RemoteResultEnum.SBCS_DM_CW.getCode(), "设备厂商代码不能为空", json.getString("request_id"), "3.0");
        }

        if (!manufacturerMap.containsKey(sbcsdm)) {
            log.error("[checkSecurity] sbcsdm is empty ! manufacturerMap= {} sbcsdm= {}", manufacturerMap, sbcsdm);
            return RemoteResultUtils.error(RemoteResultEnum.SBCS_DM_CW.getCode(), "设备厂商未授权", json.getString("request_id"), "3.0");
        }

        if (forbManufacturerMap.containsKey(sbcsdm)) {
            log.error("[checkSecurity] sbcsdm is forbidn ! forbManufacturerMap={} sbcsdm={}", forbManufacturerMap, sbcsdm);
            return RemoteResultUtils.error(RemoteResultEnum.SBCS_DM_CW.getCode(), "设备厂商禁止访问", json.getString("request_id"), "3.0");
        }

        DeviceManufacturer manufacturer = (DeviceManufacturer) manufacturerMap.get(sbcsdm);
        String clientId = json.getString("client_id");
        if (StringUtils.isBlank(clientId)) {
            log.error("[checkSecurity] client_id is empty !");
            return RemoteResultUtils.error(RemoteResultEnum.ID_CW.getCode(), "设备厂商禁止访问", json.getString("request_id"), "3.0");
        }

        //请求方ID验证错误
        String client_id = manufacturer.getClient_id();
        if (!clientId.equals(client_id)) {
            log.error("[checkSecurity] client_id is error ! client_id={} curClientId={}", client_id, clientId);
            return RemoteResultUtils.error(RemoteResultEnum.ID_CW, json.getString("request_id"), "3.0");
        }

        String clientSectet = json.getString("client_secret");
        if (StringUtils.isBlank(clientSectet)) {
            log.error("[checkSecurity] client_secret is empty !");
            return RemoteResultUtils.error(RemoteResultEnum.ID_KEY_BF.getCode(), "client_secret不能为空", json.getString("request_id"), "3.0");
        }

        //ID与密钥不符
        String client_secret = manufacturer.getClient_sectet();
        if (!clientSectet.equals(client_secret)) {
            log.error("[checkSecurity] client_sectet  is error !clientSectet={} client_secret={}", clientSectet, client_secret);
            return RemoteResultUtils.error(RemoteResultEnum.ID_KEY_BF, json.getString("request_id"), "3.0");
        }

        Integer zyycjdm = json.getInteger("zyycjdm");
        Integer zyqsqkdm = json.getInteger("zyqsqkdm");
        String hmzp_zy = json.getString("hmzp_zy");
        if ((Iris.EyeFlagEnum.BOTH_EYE.getCode().equals(zyycjdm) || Iris.EyeFlagEnum.LEFT_EYE.getCode().equals(zyycjdm))
                && Iris.EyeStatusEnum.NORMAL.getCode().equals(zyqsqkdm) && StrUtil.isNotEmpty(hmzp_zy)) {

            try {
                if (StrUtil.isNotEmpty(hmzp_zy)) {
                    String imageBase64Format = ImageUtil.getImageBase64Format(hmzp_zy);
                    if (!regImgFormat.equalsIgnoreCase(imageBase64Format)) {
                        log.warn("[checkSecurity]  hmzp_zy  imageBase64Format={} regImgFormat={}", imageBase64Format, regImgFormat);
                        return RemoteResultUtils.error(RemoteResultEnum.QTYZ_CW.getCode(), "左眼图像格式错误", json.getString("request_id"), "3.0");
                    }
                } else {
                    return RemoteResultUtils.error(RemoteResultEnum.TY_QTCW.getCode(), "左眼图像不能为空", json.getString("request_id"), "3.0");
                }
            } catch (Exception e) {
                log.error("[checkSecurity]  hmzp_zy format error ! hmzp_zy={} errMsg={}", hmzp_zy, e.toString());
                return RemoteResultUtils.error(RemoteResultEnum.QTYZ_CW.getCode(), "左眼图像异常", json.getString("request_id"), "3.0");
            }
        }

        Integer yyqsqkdm = json.getInteger("yyqsqkdm");
        String hmzp_yy = json.getString("hmzp_yy");
        if ((Iris.EyeFlagEnum.BOTH_EYE.getCode().equals(zyycjdm) || Iris.EyeFlagEnum.RIGHT_EYE.getCode().equals(zyycjdm))
                && Iris.EyeStatusEnum.NORMAL.getCode().equals(yyqsqkdm) && StrUtil.isNotEmpty(hmzp_yy)) {
            try {
                if (StrUtil.isNotEmpty(hmzp_yy)) {
                    String imageBase64Format = ImageUtil.getImageBase64Format(hmzp_yy);
                    if (!regImgFormat.equalsIgnoreCase(imageBase64Format)) {
                        log.warn("[checkSecurity]  hmzp_yy  imageBase64Format={} regImgFormat={}", imageBase64Format, regImgFormat);
                        return RemoteResultUtils.error(RemoteResultEnum.QTYZ_CW.getCode(), "右眼图像格式错误", json.getString("request_id"), "3.0");
                    }
                } else {
                    return RemoteResultUtils.error(RemoteResultEnum.TY_QTCW.getCode(), "右眼图像不能为空", json.getString("request_id"), "3.0");
                }
            } catch (Exception e) {
                log.error("[checkSecurity]   hmzp_yy format error ! hmzp_yy={} errMsg={}", hmzp_yy, e.toString());
                return RemoteResultUtils.error(RemoteResultEnum.QTYZ_CW.getCode(), "右眼图像异常", json.getString("request_id"), "3.0");
            }
        }

        String request_id = json.getString("request_id");
        if (StrUtil.isEmpty(request_id)) {
            log.error("[checkSecurity] request_id is empty !");
            return RemoteResultUtils.error(RemoteResultEnum.TY_QTCW.getCode(), "request_id error !", request_id, "3.0");
        }

        if (!request_id.contains(deptCode) || !request_id.contains(sbcsdm)) {
            log.error("[checkSecurity] request_id is empty ! request_id= {} deptCode= {} sbcsdm= {}", request_id, deptCode, sbcsdm);
            return RemoteResultUtils.error(RemoteResultEnum.TY_QTCW.getCode(), "request_id 错误！", request_id, "3.0");
        }

        //采集编号验证
        String cjbh = json.getString("cjbh");
        if (StrUtil.isEmpty(cjbh) || !cjbh.startsWith("HM") || !cjbh.contains(deptCode)) {
            log.error("[checkSecurity] cjbh is error ! cjbh={}", cjbh);
            return RemoteResultUtils.error(RemoteResultEnum.TY_QTCW.getCode(), "采集编号错误！", request_id, "3.0");
        }

        return point.proceed();
    }


    /**
     * 启动后加载
     */
    @Override
    public void run(String... args) throws Exception {
        initManufacturers();
        inintOrganizations();
        initSecurityConfig();
    }

    /**
     * 发生修改时重新加载
     */
    @After(SECURITY_POINT_CUT)
    public void checkSecurity() {
        initSecurityConfig();
        log.info("[ checkSecurity ] ");
    }

    /**
     * 更新组织信息
     */
    @After(ORG_POINT_CUT)
    public void reoladOrganization() {
        inintOrganizations();
        log.info("[ reoladOrganization ]");
    }

    /**
     * 更新厂商信息
     */
    @After(DEV_MANU_POINT_CUT)
    public void reoladDeviceManufacturer() {
        initManufacturers();
        log.info("[ reoladDeviceManufacturer ]");
    }

    /**
     * 获取系统所有允许组织
     *
     * @return
     */
    private void inintOrganizations() {
        List<Organization> list = SpringContextUtils.getBean(OrganizationService.class).findAll(Data.StatusEnum.ENABLE.getCode());
        if (CollUtil.isNotEmpty(list)) {
            //组织code与组织的key、value键值map
            organizationMap = list.stream().collect(Collectors.toMap(Organization::getCode, a -> a, (k1, k2) -> k1));
        }
        //初始化数据检查
        organizationMap = (organizationMap == null) ? new HashMap<>() : organizationMap;
        log.info("[ inintOrganizations ] organizationMap= {}", organizationMap);
    }

    /**
     * 获取系统所有允许组织
     *
     * @return
     */
    private void initManufacturers() {
        List<DeviceManufacturer> list = SpringContextUtils.getBean(DeviceManufacturerService.class).findAll(Data.StatusEnum.ENABLE.getCode());
        if (CollUtil.isNotEmpty(list)) {
            manufacturerMap = list.stream().collect(Collectors.toMap(DeviceManufacturer::getSbcsdm, a -> a, (k1, k2) -> k1));
            manufacturerIdMap = list.stream().collect(Collectors.toMap(DeviceManufacturer::getId, a -> a, (k1, k2) -> k1));
            forbManufacturerMap = list.stream().filter(deviceManufacturer -> !deviceManufacturer.isEnable()).collect(Collectors.toMap(DeviceManufacturer::getSbcsdm, a -> a, (k1, k2) -> k1));
        }
        //检查序列化数据
        forbOrgaMap = (forbOrgaMap == null) ? new HashMap<>() : forbOrgaMap;
        manufacturerMap = (manufacturerMap == null) ? new HashMap<>() : manufacturerMap;
        forbManufacturerMap = (forbManufacturerMap == null) ? new HashMap<>() : forbManufacturerMap;
        log.info("[ initManufacturers ] manufacturerMap= {}", manufacturerMap);
        log.info("[ initManufacturers ] manufacturerIdMap= {}", manufacturerIdMap);
        log.info("[ initManufacturers ] forbManufacturerMap= {}", forbManufacturerMap);
    }

    /**
     * 初始化全局安全配置
     */
    private void initSecurityConfig() {

        SecurityConfig securityConfig = SpringContextUtils.getBean(SecurityConfigService.class).getSecurityConfig();
        if (securityConfig == null) {
            return;
        }

        forbidIP = securityConfig.getForbidIP();

        List<SecurityConfig.ForbidUser> forbidDeptList = securityConfig.getForbidUserList();
        if (CollUtil.isNotEmpty(forbidDeptList)) {
            forbUserMap = forbidDeptList.stream().collect(Collectors.toMap(SecurityConfig.ForbidUser::getCjr_gmsfhm, a -> a, (k1, k2) -> k1));
        }


        List<SecurityConfig.ForbidOrg> forbidOrganizationList = securityConfig.getForbidOrganization();
        if (CollUtil.isNotEmpty(forbidOrganizationList)) {
            forbOrgaMap = forbidOrganizationList.stream().collect(Collectors.toMap(SecurityConfig.ForbidOrg::getCode, a -> a, (k1, k2) -> k1));
        }

        registerModel = securityConfig.getRegisterModel();
        regcognitionModel = securityConfig.getRegcognitionModel();
        checkModel = securityConfig.getCheckModel();

        //序列化数据检查
        forbidIP = (forbidIP == null) ? new ArrayList<>() : forbidIP;
        forbUserMap = (forbUserMap == null) ? new HashMap<>() : forbUserMap;
        forbOrgaMap = (forbOrgaMap == null) ? new HashMap<>() : forbOrgaMap;
        registerModel = (registerModel == null) ? DataEntrySystemModeEnum.LOCAL_REMOTE.getCode() : registerModel;
        checkModel = (checkModel == null) ? DataEntrySystemModeEnum.LOCAL_REMOTE.getCode() : checkModel;
        regcognitionModel = (regcognitionModel == null) ? DataEntrySystemModeEnum.LOCAL_REMOTE.getCode() : regcognitionModel;

        log.info("[ initSecurityConfig ] forbidIP= {}", forbidIP);
        log.info("[ initSecurityConfig ] forbUserMap= {}", forbUserMap);
        log.info("[ initSecurityConfig ] forbOrgaMap= {}", forbOrgaMap);

        log.info("[ initSecurityConfig ] registerModel= {}", registerModel);
        log.info("[ initSecurityConfig ] regcognitionModel= {}", regcognitionModel);
        log.info("[ initSecurityConfig ] checkModel= {}", checkModel);
    }
}

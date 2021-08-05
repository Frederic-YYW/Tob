package com.hw.tobcore.aop;

import com.iristar.center.entity.base.Admin;
import com.iristar.center.entity.base.DeviceManufacturer;
import com.iristar.center.entity.base.Institution;
import com.iristar.center.entity.base.Organization;
import com.iristar.center.enums.ResultEnum;
import com.iristar.center.service.DeviceManufacturerService;
import com.iristar.center.service.InstitutionService;
import com.iristar.center.service.OrganizationService;
import com.iristar.center.util.Constants;
import com.iristar.center.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:检查新建管理员机构信息配置
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/12/23 11:27
 */
@Aspect
@Component
@Slf4j
public class AdminInstitutionAop {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private InstitutionService institutionService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private DeviceManufacturerService deviceManufacturerService;

    private final String POINT_CUT =
            "(execution(public * com.iristar.center.api.v1.RegisterApiV1.list(..)))"
                    + " || (execution(public * com.iristar.center.api.v1.RegisterApiV1.register(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.RegisterApiV1.staCouByManufacturer(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.RegisterApiV1.staCouByOrganization(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.RegisterApiV1.staCouByUser(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.RegisterApiV1.batchQuery(..)))"

                    + " || (execution(public * com.iristar.center.api.v1.CheckApiV1.checkIris(..)))"
                    + " || (execution(public * com.iristar.center.api.v1.CheckApiV1.list(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.CheckApiV1.staCouByManufacturer(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.CheckApiV1.staCouByOrganization(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.CheckApiV1.staCouByUser(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.CheckApiV1.batchQuery(..)))"

                    + " || (execution(public * com.iristar.center.api.v1.RecognitionApiV1.recognize(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.RecognitionApiV1.staCouByManufacturer(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.RecognitionApiV1.staCouByOrganization(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.RecognitionApiV1.staCouByUser(..)))"
//                    + " || (execution(public * com.iristar.center.api.v1.RecognitionApiV1.batchQuery(..)))"
                    + " || (execution(public * com.iristar.center.api.v1.RecognitionApiV1.list(..)))";

    /**
     * 检查管理员信息，防止新建管理员信息不全
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around(POINT_CUT)
    public Object checkSecurity(ProceedingJoinPoint point) throws Throwable {
        Admin admin = (Admin) request.getSession().getAttribute(Constants.User.COOKIE_USER);
        if (admin == null) {
            log.error("[checkSecurity ] errmsg={}", "Administrator message is null");
            return ResultUtils.error(ResultEnum.PARAM_ERROR, "管理员信息为空");
        }

        //检查管理员机构信息是否完善
        Institution institution = institutionService.findByPrimaryKey(admin.getInstitutionId());
        if (institution == null) {
            log.error("[checkSecurity ] institutionId={}", admin.getInstitutionId());
            return ResultUtils.error(ResultEnum.PARAM_WARN, "请完善管理员机构信息");
        }

        //检查机构信息是否被删除
        Organization organization = organizationService.findByCode(institution.getDeptCode());
        if (organization == null) {
            log.error("[checkSecurity ] institutionId 已被删除，无法使用，请更新！ institution={}", institution);
            return ResultUtils.error(ResultEnum.PARAM_WARN, "机构信息已失效，请更新！");
        }

        //检查设备厂商信息是否被删除
        DeviceManufacturer deviceManufacturer = deviceManufacturerService.findByCode(institution.getDeviceManuCode());
        if (deviceManufacturer == null) {
            log.error("[checkSecurity ] deviceManufacturer 已被删除，无法使用，请更新！ deviceManufacturer={}", deviceManufacturer);
            return ResultUtils.error(ResultEnum.PARAM_WARN, "设备厂商信息已失效，请更新！");
        }

        return point.proceed();
    }
}

package com.hw.tobcore.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.iristar.center.entity.base.*;
import com.iristar.center.entity.gonganbase.RegReqBase;
import com.iristar.center.entity.siyue.SyConfig;
import com.iristar.center.entity.siyue.SyRegReq;
import com.iristar.center.entity.vo.CheckInfoVO;
import com.iristar.center.entity.vo.RecoInfoVO;
import com.iristar.center.entity.vo.RegInfoVO;
import com.iristar.center.enums.Data;
import com.iristar.center.enums.Iris;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Slf4j
public class ConversionUtil {

    public static RegReqBase convert2RegReq(SyConfig config, RegInfo regInfo, RegReqBase regReqBase) {

        regReqBase.setZjbz(regInfo.getZjbz());
        regReqBase.setCjr_gmsfhm(regInfo.getCjr_gmsfhm());
        regReqBase.setCjr_xm(regInfo.getXm());
        regReqBase.setLrbz(regInfo.getLrbz());
        regReqBase.setQzcjbz(regInfo.getQzcjbz());


        regReqBase.setVersion(StrUtil.isEmpty(regInfo.getVersion()) ? "1.0" : regInfo.getVersion());

//        regReqBase.setZjbz(data.getString("cardFlag"));   //TODO 1-有证 0-无证
//        regReqBase.setCjr_gmsfhm(data.getString("regCardno")); //TODO
//        regReqBase.setCjr_xm(data.getString("regRealname"));
//        regReqBase.setDzmc(data.getString("regInstitution")); //TODO 非必填
//        regReqBase.setCjcd(data.getString("regInstitutionCode")); //TODO
//        regReqBase.setLrbz(data.getString("regFlag"));  //TODO  1-读证录入 2-手工录入 3-警综录入
//        regReqBase.setQzcjbz(data.getString("forceRegFlag")); //TODO 1-强制，0-正常
//        regReqBase.setSbbh(data.getString("deviceno")); //TODO

//        regReqBase.setBcjr_csrq(regInfo.getBcjrCsrq());
//        regReqBase.setBcjr_gj(regInfo.getBcjrGj());
//        regReqBase.setBcjr_hjdz(regInfo.getBcjrHjdz());
//        regReqBase.setBcjr_jzdz(regInfo.getBcjrJzdz()); //TODO
//        regReqBase.setBcjr_mz(regInfo.getBcjrMz());
//        regReqBase.setBcjr_rylb(regInfo.getBcjrRylb());
//        regReqBase.setBcjr_sjhm1(regInfo.getBcjrSjhm1());
//        regReqBase.setBcjr_sjhm2(regInfo.getBcjrSjhm2());
//        regReqBase.setBcjr_xb(regInfo.getBcjrXb());
//        regReqBase.setBcjr_xm(regInfo.getBcjrXm());
//        regReqBase.setBcjr_zjhm(regInfo.getBcjrZjhm());
//        regReqBase.setBcjr_zjlxdm(regInfo.getBcjrZjlxdm());
//        regReqBase.setBcjr_zjqfjg(regInfo.getBcjrZjqfjg());
//        regReqBase.setBcjr_zjyxqx(regInfo.getBcjrZjyxqx());
        regReqBase.setZjzp(regInfo.getZjzp());


//        regReqBase.setUser_deptname(regInfo.getUser_deptname());    //请求方_公安机关名称
//        regReqBase.setUser_dept(regInfo.getUser_dept());      //请求方_公安机关机构代码
        regReqBase.setUser_id(regInfo.getUser_id());   //请求人_公民身份号码
        regReqBase.setHmcjsbxhdm(regInfo.getHmcjsbxhdm()); //虹膜采集设备型号代码
        regReqBase.setSbbh(regInfo.getSbbh());  //设备编号
        regReqBase.setSbcsdm(regInfo.getSbcsdm());   //厂商代码
        regReqBase.setDzmc(regInfo.getDzmc()); //业务发生地点_地址名称
        regReqBase.setCjcd(regInfo.getCjcd()); //采集场地代码

        regReqBase.setJd(regInfo.getJd());
        regReqBase.setWd(regInfo.getWd());

        regReqBase.setYwfssj(DateUtil.getUniqueDateTime());

        regReqBase.setYy_xxzlpf("" + regInfo.getYy_xxzlpf());
        regReqBase.setYyqsqkdm(regInfo.getYyqsqkdm() + "");  //TODO
        regReqBase.setZyqsqkdm(regInfo.getZyqsqkdm() + "");  //TODO
        regReqBase.setZyycjdm(regInfo.getZyycjdm() + "");
        regReqBase.setZy_xxzlpf("" + regInfo.getZy_xxzlpf());
        regReqBase.setCjbz(regInfo.getCjbz());
        regReqBase.setCjtphs("" + regInfo.getCjtphs()); //TODO
        return regReqBase;
    }

    public static RecoInfo toRecoInfo(RecoInfoVO regInfoVO) {
        RecoInfo recoInfo = new RecoInfo();
        if (StringUtils.isNotBlank(regInfoVO.getId())) {
            recoInfo.setId(regInfoVO.getId());
        }
        recoInfo.setSn(regInfoVO.getSn());
        recoInfo.setAddress(regInfoVO.getAddress());
        recoInfo.setCjr_gmsfhm(regInfoVO.getRegCardno());//采集人身份证号
        recoInfo.setQzcjbz(regInfoVO.getForceRegFlag());//强制采集标志
        recoInfo.setZyqsqkdm(Integer.valueOf(regInfoVO.getLeftEyeStatus())); //左眼缺失情况代码
        recoInfo.setYyqsqkdm(Integer.valueOf(regInfoVO.getRightEyeStatus())); //右眼缺失情况代码
        recoInfo.setZyycjdm(regInfoVO.getBothEyeStatus()); //左右眼采集代码
        recoInfo.setHmzp_zy(regInfoVO.getLeftEyeImg());//虹膜照片左眼
        recoInfo.setSjly(regInfoVO.getOffline());
        recoInfo.setHmzp_yy(regInfoVO.getRightEyeImg());//虹膜照片右眼
        //recoInfo.setCjtphs(String.valueOf(regInfoVO.getProcessTime())); //采集图片耗时
        recoInfo.setCjtphs("3.032"); //采集图片耗时
        recoInfo.setZy_xxzlpf(String.valueOf(regInfoVO.getLeftEyeScore())); //左眼虹膜照片信息质量评分
        recoInfo.setYy_xxzlpf(String.valueOf(regInfoVO.getRightEyeScore())); //右眼虹膜照片信息质量评分
        recoInfo.setSjzt(Iris.Recognize.ResultStatusEnum.INIT.getCode());
        recoInfo.setBdzt(Iris.Recognize.ResultStatusEnum.INIT.getCode());
        recoInfo.setVersion(StrUtil.isEmpty(regInfoVO.getVersion()) ? "1.0" : regInfoVO.getVersion());
        return recoInfo;
    }

    public static CheckInfo toCheckInfo(CheckInfoVO checkInfoVO) {
        CheckInfo checkInfo = new CheckInfo();
        if (StringUtils.isNotBlank(checkInfoVO.getId())) {
            checkInfo.setId(checkInfoVO.getId());
        }
        checkInfo.setSn(checkInfoVO.getSn());
        checkInfo.setAddress(checkInfoVO.getAddress());
        checkInfo.setBcjr_zjhm(checkInfoVO.getCardno()); //被采集人身份证号
        checkInfo.setCjr_xm(checkInfoVO.getRegRealname());//采集人姓名
        checkInfo.setCjr_xm(checkInfoVO.getRegCardno());//采集人身份证号
        checkInfo.setLrbz(checkInfoVO.getRegFlag());//录入标志
        checkInfo.setQzcjbz(checkInfoVO.getForceRegFlag());//强制采集标志
        checkInfo.setBcjr_zjlxdm(checkInfoVO.getCardType()); //被采集人证件类型代码
        checkInfo.setBcjr_zjhm(checkInfoVO.getCardno()); //被采集人证件号码
        checkInfo.setBcjr_xm(checkInfoVO.getRealname()); //被采集人姓名
        checkInfo.setBcjr_xb(checkInfoVO.getSex()); //被采集人性别
        checkInfo.setBcjr_mz(checkInfoVO.getNation()); //被采集人民族
        checkInfo.setBcjr_gj(checkInfoVO.getCountry()); //被采集人国家
        checkInfo.setBcjr_csrq(checkInfoVO.getBirthday()); //被采集人出生日期
        checkInfo.setZjzp(checkInfoVO.getCardImg());
        checkInfo.setZyqsqkdm(checkInfoVO.getLeftEyeStatus()); //左眼缺失情况代码
        checkInfo.setYyqsqkdm(checkInfoVO.getRightEyeStatus()); //右眼缺失情况代码
        checkInfo.setZyycjdm(checkInfoVO.getBothEyeStatus()); //左右眼采集代码
        checkInfo.setHmzp_zy(checkInfoVO.getLeftEyeImg());//虹膜照片左眼
        checkInfo.setHmzp_yy(checkInfoVO.getRightEyeImg());//虹膜照片右眼
        checkInfo.setCjtphs(String.valueOf(checkInfoVO.getProcessTime())); //采集图片耗时
        checkInfo.setZy_xxzlpf(String.valueOf(checkInfoVO.getLeftEyeScore())); //左眼虹膜照片信息质量评分
        checkInfo.setYy_xxzlpf(String.valueOf(checkInfoVO.getRightEyeScore())); //右眼虹膜照片信息质量评分
        checkInfo.setSjly(checkInfoVO.getOffline());
        checkInfo.setSjzt(Iris.Check.ResultStatusEnum.INIT.getCode());
        checkInfo.setBdzt(Iris.Check.ResultStatusEnum.INIT.getCode());
        checkInfo.setId(IDGenerator.getID());
        checkInfo.setZjbz(checkInfoVO.getCardFlag());
        checkInfo.setVersion(StrUtil.isEmpty(checkInfoVO.getVersion()) ? "1.0" : checkInfoVO.getVersion());
        return checkInfo;
    }

    public static CheckInfo toCheckInfo(CheckInfoVO checkInfoVO, Admin admin) {
        CheckInfo checkInfo = new CheckInfo();
        if (StringUtils.isNotBlank(checkInfoVO.getId())) {
            checkInfo.setId(checkInfoVO.getId());
        }
        checkInfo.setBcjr_zjhm(checkInfoVO.getCardno()); //被采集人身份证号
        checkInfo.setCjr_xm(checkInfoVO.getRegRealname());//采集人姓名
        checkInfo.setCjr_xm(checkInfoVO.getRegCardno());//采集人身份证号
        checkInfo.setLrbz(checkInfoVO.getRegFlag());//录入标志
        checkInfo.setQzcjbz(checkInfoVO.getForceRegFlag());//强制采集标志
        checkInfo.setBcjr_zjlxdm(checkInfoVO.getCardType()); //被采集人证件类型代码
        checkInfo.setBcjr_zjhm(checkInfoVO.getCardno()); //被采集人证件号码
        checkInfo.setBcjr_xm(checkInfoVO.getRealname()); //被采集人姓名
        checkInfo.setBcjr_xb(checkInfoVO.getSex()); //被采集人性别
        checkInfo.setBcjr_mz(checkInfoVO.getNation()); //被采集人民族
        checkInfo.setBcjr_gj(checkInfoVO.getCountry()); //被采集人国家
        checkInfo.setBcjr_csrq(checkInfoVO.getBirthday()); //被采集人出生日期
        checkInfo.setZjzp(checkInfoVO.getCardImg());
        checkInfo.setZyqsqkdm(checkInfoVO.getLeftEyeStatus()); //左眼缺失情况代码
        checkInfo.setYyqsqkdm(checkInfoVO.getRightEyeStatus()); //右眼缺失情况代码
        checkInfo.setZyycjdm(checkInfoVO.getBothEyeStatus()); //左右眼采集代码
        checkInfo.setHmzp_zy(checkInfoVO.getLeftEyeImg());//虹膜照片左眼
        checkInfo.setHmzp_yy(checkInfoVO.getRightEyeImg());//虹膜照片右眼
        checkInfo.setCjtphs(String.valueOf(checkInfoVO.getProcessTime())); //采集图片耗时
        checkInfo.setZy_xxzlpf(String.valueOf(checkInfoVO.getLeftEyeScore())); //左眼虹膜照片信息质量评分
        checkInfo.setYy_xxzlpf(String.valueOf(checkInfoVO.getRightEyeScore())); //右眼虹膜照片信息质量评分
        checkInfo.setSjly(checkInfoVO.getOffline());

        //设置采集人信息
        checkInfo.setCjr_gmsfhm(admin.getCardno());
        checkInfo.setCjr_xm(admin.getRealname());
        checkInfo.setCjr_gmsfhm(admin.getCardType());
        checkInfo.setSjzt(Iris.Check.ResultStatusEnum.INIT.getCode());
        checkInfo.setBdzt(Iris.Check.ResultStatusEnum.INIT.getCode());
        return checkInfo;
    }

    /*-------------------------------------------------------------------------------------------------------*/

    public static RegInfo toRegInfo(RegInfoVO regInfoVO) {
        RegInfo regInfo = new RegInfo();

        if (StringUtils.isNotBlank(regInfoVO.getId())) {
            regInfo.setId(regInfoVO.getId());
        }
        regInfo.setZjhm(regInfoVO.getCardno()); //被采集人身份证号
        regInfo.setXm(regInfoVO.getRegRealname());//被采集人姓名
        regInfo.setCjr_gmsfhm(regInfoVO.getRegCardno());//采集人身份证号
        regInfo.setLrbz(regInfoVO.getRegFlag());//录入标志
        regInfo.setQzcjbz(regInfoVO.getForceRegFlag());//强制采集标志
        regInfo.setZjlx(regInfoVO.getCardType()); //被采集人证件类型代码
        regInfo.setZjhm(regInfoVO.getCardno()); //被采集人证件号码
        regInfo.setXm(regInfoVO.getRealname()); //被采集人姓名
        regInfo.setXb(regInfoVO.getSex()); //被采集人性别
        regInfo.setMz(regInfoVO.getNation()); //被采集人民族
        regInfo.setGj(regInfoVO.getCountry()); //被采集人国家
        regInfo.setCsrq(regInfoVO.getBirthday()); //被采集人出生日期
        regInfo.setQfjg(regInfoVO.getInstitution()); //被采集人签发机关
        regInfo.setYxqx(regInfoVO.getExpireAt()); //有效期限
        regInfo.setHjdz(regInfoVO.getHometown()); //被采集人户籍地址
        regInfo.setJzdz(regInfoVO.getHomeAddress()); //被采集人居住地址
        regInfo.setSjh1(regInfoVO.getPhone1()); //手机号码1
        regInfo.setSjh2(regInfoVO.getPhone2()); //手机号码2
        regInfo.setZjzp(regInfoVO.getCardImg());
        if (regInfoVO.getLeftEyeStatus() != null) {
            regInfo.setZyqsqkdm(String.valueOf(regInfoVO.getLeftEyeStatus())); //左眼缺失情况代码
        }
        if (regInfoVO.getRightEyeStatus() != null) {
            regInfo.setYyqsqkdm(String.valueOf(regInfoVO.getRightEyeStatus())); //右眼缺失情况代码
        }
        if (regInfoVO.getBothEyeStatus() != null) {
            regInfo.setZyycjdm(String.valueOf(regInfoVO.getBothEyeStatus())); //双眼缺失情况代码
        }
        regInfo.setRylb(regInfoVO.getUserGroup());
        regInfo.setHmzp_zy(regInfoVO.getLeftEyeImg());//虹膜照片左眼
        regInfo.setHmzp_yy(regInfoVO.getRightEyeImg());//虹膜照片右眼
        // regInfo.setCjtphs(String.valueOf(regInfoVO.getProcessTime())); //采集图片耗时
        regInfo.setZy_xxzlpf(String.valueOf(regInfoVO.getLeftEyeScore())); //左眼虹膜照片信息质量评分
        regInfo.setYy_xxzlpf(String.valueOf(regInfoVO.getRightEyeScore())); //右眼虹膜照片信息质量评分
        regInfo.setCjbz(regInfoVO.getMessage()); //采集备注
        regInfo.setSjly(regInfoVO.getOffline()); //采集备注
        regInfo.setYhzt(Data.StatusEnum.ENABLE.getCode());
        regInfo.setBdzt(regInfoVO.getLocalStatus() == null ? Iris.Register.ResultStatusEnum.INIT.getCode() : regInfoVO.getLocalStatus()); //本地状态 都默认初始
        regInfo.setSjzt(regInfoVO.getStatus() == null ? Iris.Register.ResultStatusEnum.INIT.getCode() : regInfoVO.getStatus());  //上级状态都默认初始
        regInfo.setZjbz(regInfoVO.getRegFlag());
        regInfo.setCjtphs("2.032");
        regInfo.setVersion(StrUtil.isEmpty(regInfoVO.getVersion()) ? "1.0" : regInfoVO.getVersion());
        return regInfo;
    }


    public static RegInfo toRegInfoForUpdate(RegInfoVO regInfoVO) {
        RegInfo regInfo = new RegInfo();
        if (StringUtils.isNotBlank(regInfoVO.getId())) {
            regInfo.setId(regInfoVO.getId());
        }

        if (regInfoVO.getRegCardno() != null) {
            regInfo.setCjr_gmsfhm(regInfoVO.getRegCardno()); //采集人身份证号
        }

        if (regInfoVO.getCardno() != null) {
            regInfo.setZjhm(regInfoVO.getCardno()); //被采集人身份证号
        }

        if (regInfoVO.getRegRealname() != null) {
            regInfo.setXm(regInfoVO.getRegRealname()); //采集人姓名
        }

        if (regInfoVO.getRealname() != null) {
            regInfo.setXm(regInfoVO.getRealname()); //被采集人姓名
        }

        if (regInfoVO.getRegFlag() != null) {
            regInfo.setLrbz(regInfoVO.getRegFlag()); //录入标志
        }
        if (regInfoVO.getRegFlag() != null) {
            regInfo.setQzcjbz(regInfoVO.getForceRegFlag());//强制采集标志
        }
        if (regInfoVO.getCardType() != null) {
            regInfo.setZjlx(regInfoVO.getCardType()); //被采集人证件类型代码
        }


        if (regInfoVO.getSex() != null) {
            regInfo.setXb(regInfoVO.getSex() + ""); //被采集人性别
        }
        if (regInfoVO.getNation() != null) {
            regInfo.setMz(regInfoVO.getNation()); //被采集人民族
        }
        if (regInfoVO.getCountry() != null) {
            regInfo.setGj(regInfoVO.getCountry()); //被采集人国家
        }
        if (regInfoVO.getBirthday() != null) {
            regInfo.setCsrq(regInfoVO.getBirthday()); //被采集人出生日期
        }
        if (regInfoVO.getInstitution() != null) {
            regInfo.setQfjg(regInfoVO.getInstitution()); //被采集人签发机关
        }
        if (regInfoVO.getExpireAt() != null) {
            regInfo.setYxqx(regInfoVO.getExpireAt()); //有效期限
        }
        if (regInfoVO.getHometown() != null) {
            regInfo.setHjdz(regInfoVO.getHometown()); //被采集人户籍地址
        }
        if (regInfoVO.getHomeAddress() != null) {
            regInfo.setJzdz(regInfoVO.getHomeAddress()); //被采集人居住地址
        }
        if (regInfoVO.getPhone1() != null) {
            regInfo.setSjh1(regInfoVO.getPhone1()); //手机号码1
        }
        if (regInfoVO.getPhone2() != null) {
            regInfo.setSjh2(regInfoVO.getPhone2()); //手机号码2
        }
        //证件照片不允许置空，前端没有删除
        if (StringUtils.isNotBlank(regInfoVO.getCardImg())) {
            regInfo.setZjzp(regInfoVO.getCardImg());
        }
        if (regInfoVO.getLeftEyeStatus() != null) {
            regInfo.setZyqsqkdm(String.valueOf(regInfoVO.getLeftEyeStatus())); //左眼缺失情况代码
        }
        if (regInfoVO.getRightEyeStatus() != null) {
            regInfo.setYyqsqkdm(String.valueOf(regInfoVO.getRightEyeStatus())); //右眼缺失情况代码
        }
        if (regInfoVO.getBothEyeStatus() != null) {
            regInfo.setZyycjdm(String.valueOf(regInfoVO.getBothEyeStatus())); //双眼缺失情况代码
        }
        if (regInfoVO.getUserGroup() != null) {
            regInfo.setRylb(regInfoVO.getUserGroup());
        }
        if (regInfoVO.getLeftEyeImg() != null) {
            regInfo.setHmzp_zy(regInfoVO.getLeftEyeImg());//虹膜照片左眼
        }
        if (regInfoVO.getRightEyeImg() != null) {
            regInfo.setHmzp_yy(regInfoVO.getRightEyeImg());//虹膜照片右眼
        }
        if (regInfoVO.getProcessTime() != 0) {
            regInfo.setCjtphs(String.valueOf(regInfoVO.getProcessTime())); //采集图片耗时
        }
        if (regInfoVO.getLeftEyeScore() != 0) {
            regInfo.setZy_xxzlpf(String.valueOf(regInfoVO.getLeftEyeScore())); //左眼虹膜照片信息质量评分
        }
        if (regInfoVO.getRightEyeScore() != 0) {
            regInfo.setYy_xxzlpf(String.valueOf(regInfoVO.getRightEyeScore())); //右眼虹膜照片信息质量评分
        }
        if (regInfoVO.getMessage() != null) {
            regInfo.setCjbz(regInfoVO.getMessage()); //采集备注
        }
        if (regInfoVO.getOffline() != null) {
            regInfo.setSjly(regInfoVO.getOffline()); //采集备注
        }

        if (regInfoVO.getLocalStatus() != null) {
            regInfo.setBdzt(regInfoVO.getLocalStatus()); //本地状态 都默认初始
        }
        if (regInfoVO.getStatus() != null) {
            regInfo.setSjzt(regInfoVO.getStatus());  //上级状态都默认初始
        }

        return regInfo;
    }


    public static RegInfo SyRegReqConversion(SyRegReq syRegReq) {
        RegInfo regInfo = new RegInfo();
        BeanUtils.copyProperties(syRegReq, regInfo);
        regInfo.setRequest_id(syRegReq.getRequest_id());    //54位任务id
        regInfo.setYwfssj(syRegReq.getYwfssj());    //业务发生时间
        regInfo.setUser_id(syRegReq.getUser_id());    //请求人_公民身份号码
//        regInfo.setUser_deptname(syRegReq.getUser_deptname());   //请求方_公安机关名称
//        regInfo.setUser_dept(syRegReq.getUser_dept());  //请求方_公安机关机构代码
        regInfo.setCjr_xm(syRegReq.getCjr_xm());  //采集人姓名
        regInfo.setCjr_gmsfhm(syRegReq.getCjr_gmsfhm()); //采集人身份证号
        regInfo.setRylb(syRegReq.getBcjr_rylb());  //被采集虹膜人员类别代码
        regInfo.setZjlx(syRegReq.getBcjr_zjlxdm()); //被采集人证件类型代码
        regInfo.setZjhm(syRegReq.getBcjr_zjhm()); //被采集人证件号码
        regInfo.setXm(syRegReq.getBcjr_xm()); //被采集人姓名
        regInfo.setXb(syRegReq.getBcjr_xb()); //被采集人性别
        regInfo.setMz(syRegReq.getBcjr_mz()); //被采集人民族代码
        regInfo.setGj(syRegReq.getBcjr_gj()); //被采集人国家代码
        regInfo.setCsrq(syRegReq.getBcjr_csrq()); //被采集人出生日期
        regInfo.setQfjg(syRegReq.getBcjr_zjqfjg()); //签发机关
        regInfo.setYxqx(syRegReq.getBcjr_zjyxqx()); //有效期限
        regInfo.setHjdz(syRegReq.getBcjr_hjdz()); //被采集人户籍地址
        regInfo.setJzdz(syRegReq.getBcjr_jzdz()); //被采集人居住地址
        regInfo.setSjh1(syRegReq.getBcjr_sjhm1()); //手机号码1
        regInfo.setSjh2(syRegReq.getBcjr_sjhm2()); //手机号码2
        regInfo.setHmzp_zy(syRegReq.getHmzp_zy()); //虹膜照片左眼
        regInfo.setHmzp_yy(syRegReq.getHmzp_yy()); //虹膜照片右眼
        regInfo.setZy_xxzlpf(syRegReq.getZy_xxzlpf()); //左眼虹膜照片信息质量评分
        regInfo.setYy_xxzlpf(syRegReq.getYy_xxzlpf()); //右眼虹膜照片信息质量评分
        return regInfo;
    }

    public static RegInfoVO regInfoToRegInfoVO(RegInfo regInfo) {
        RegInfoVO regInfoVO = new RegInfoVO();
        regInfoVO.setId(regInfo.getId());
        regInfoVO.setCardno(regInfo.getZjhm()); //被采集人身份证号
        regInfoVO.setRegRealname(regInfo.getXm());//被采集人姓名
        regInfoVO.setRegCardno(regInfo.getCjr_gmsfhm());//采集人身份证号
        regInfoVO.setRegFlag(regInfo.getLrbz());//录入标志
        regInfoVO.setForceRegFlag(regInfo.getQzcjbz());//强制采集标志
        regInfoVO.setCardType(regInfo.getZjlx()); //被采集人证件类型代码
        regInfoVO.setCardno(regInfo.getZjhm()); //被采集人证件号码
        regInfoVO.setUserGroup(regInfo.getRylb()); //被采集人人员类别
        regInfoVO.setRealname(regInfo.getXm()); //被采集人姓名
        regInfoVO.setSex(regInfo.getXb()); //被采集人性别
        regInfoVO.setAdminInstitution(regInfo.getUser_deptname());
        regInfoVO.setNation(regInfo.getMz()); //被采集人民族
        regInfoVO.setCountry(regInfo.getGj()); //被采集人国家
        regInfoVO.setBirthday(regInfo.getCsrq()); //被采集人出生日期
        regInfoVO.setInstitution(regInfo.getQfjg()); //被采集人签发机关
        regInfoVO.setExpireAt(regInfo.getYxqx()); //有效期限
        regInfoVO.setHometown(regInfo.getHjdz()); //被采集人户籍地址
        regInfoVO.setHomeAddress(regInfo.getJzdz()); //被采集人居住地址
        regInfoVO.setPhone1(regInfo.getSjh1()); //手机号码1
        regInfoVO.setPhone2(regInfo.getSjh2()); //手机号码2
        regInfoVO.setCardImg(regInfo.getZjzp());
        if (regInfo.getZyqsqkdm() != null) {
            regInfoVO.setLeftEyeStatus(Integer.valueOf(regInfo.getZyqsqkdm())); //左眼缺失情况代码
        }
        if (regInfo.getYyqsqkdm() != null) {
            regInfoVO.setRightEyeStatus(Integer.valueOf(regInfo.getYyqsqkdm())); //右眼缺失情况代码
        }
        if (regInfo.getZyycjdm() != null) {
            regInfoVO.setBothEyeStatus(Integer.valueOf(regInfo.getZyycjdm())); //左右眼采集代码
        }
        regInfoVO.setLeftEyeImg(regInfo.getHmzp_zy());//虹膜照片左眼
        regInfoVO.setRightEyeImg(regInfo.getHmzp_yy());//虹膜照片右眼
        if (StrUtil.isNotEmpty(regInfo.getCjtphs())) {
            regInfoVO.setProcessTime(Double.valueOf(regInfo.getCjtphs())); //采集图片耗时
        }
        if (StrUtil.isNotEmpty(regInfo.getZy_xxzlpf())) {
            regInfoVO.setLeftEyeScore(Double.valueOf(regInfo.getZy_xxzlpf())); //左眼虹膜照片信息质量评分
        }
        if (StrUtil.isNotEmpty(regInfo.getYy_xxzlpf())) {
            regInfoVO.setRightEyeScore(Double.valueOf(regInfo.getYy_xxzlpf())); //右眼虹膜照片信息质量评分
        }
        regInfoVO.setMessage(regInfo.getCjbz()); //采集备注
        // regInfoVO.set(regInfo.getOffline()); //采集备注
        regInfoVO.setStatus(regInfo.getSjzt());  //上级状态
        regInfoVO.setLocalStatus(regInfo.getBdzt()); //本地状态
        regInfoVO.setCreateAt(regInfo.getCreateAt());
        regInfoVO.setCjr_xm(regInfo.getCjr_xm());
        regInfoVO.setSbbh(regInfo.getSbbh());
        regInfoVO.setSbcsdm(regInfo.getSbcsdm());
        return regInfoVO;
    }

    public static RegInfoVO recoInfoToRegInfoVO(RecoInfo recoInfo) {
        RegInfoVO regInfoVO = new RegInfoVO();
        try {
            regInfoVO.setId(recoInfo.getId());
            List<BzryBean> bzry = recoInfo.getBzry();
            if (bzry != null && bzry.size() > 0) {
                // bzry.forEach((bzryBean) -> {
                BzryBean bzryBean = bzry.get(0);
                regInfoVO.setCardno(bzryBean.getZjhm()); //被采集人身份证号
                regInfoVO.setRegRealname(bzryBean.getXm());//被采集人姓名
                regInfoVO.setCardType(bzryBean.getZjlx()); //被采集人证件类型代码
                regInfoVO.setUserGroup(bzryBean.getRylb()); //被采集人人员类别
                regInfoVO.setCardno(bzryBean.getZjhm()); //被采集人证件号码
                regInfoVO.setRealname(bzryBean.getXm()); //被采集人姓名
                regInfoVO.setSex(bzryBean.getXb()); //被采集人性别
                regInfoVO.setNation(bzryBean.getMz()); //被采集人民族
                regInfoVO.setCountry(bzryBean.getGj()); //被采集人国家
                regInfoVO.setBirthday(bzryBean.getCsrq()); //被采集人出生日期
//                regInfoVO.setInstitution(bzryBean.getCjdwmc()); //被采集人签发机关
                regInfoVO.setExpireAt(bzryBean.getYxqx()); //有效期限
                regInfoVO.setHometown(bzryBean.getHjdz()); //被采集人户籍地址
                regInfoVO.setHomeAddress(bzryBean.getJzdz()); //被采集人居住地址
                regInfoVO.setPhone1(bzryBean.getSjh1()); //手机号码1
                regInfoVO.setPhone2(bzryBean.getSjh2()); //手机号码2
                //  });
            }
//            else
            {
                //todo 采集单位
                regInfoVO.setInstitution(recoInfo.getUser_deptname());
            }
            regInfoVO.setRegCardno(recoInfo.getCjr_gmsfhm());//采集人身份证号
            regInfoVO.setRegFlag(recoInfo.getLrbz());//录入标志
            regInfoVO.setForceRegFlag(recoInfo.getQzcjbz());//强制采集标志
            regInfoVO.setAdminName(recoInfo.getCjr_xm()); //被采集人姓名
            regInfoVO.setCardImg(recoInfo.getZjzp());
            regInfoVO.setLeftEyeStatus(recoInfo.getZyqsqkdm() == null ? 0 : Integer.valueOf(recoInfo.getZyqsqkdm())); //左眼缺失情况代码
            regInfoVO.setRightEyeStatus(recoInfo.getYyqsqkdm() == null ? 0 : Integer.valueOf(recoInfo.getYyqsqkdm())); //右眼缺失情况代码
            regInfoVO.setBothEyeStatus(recoInfo.getZyycjdm() == null ? 0 : Integer.valueOf(recoInfo.getZyycjdm())); //左右眼采集代码
            regInfoVO.setLeftEyeImg(recoInfo.getHmzp_zy());//虹膜照片左眼
            regInfoVO.setRightEyeImg(recoInfo.getHmzp_yy());//虹膜照片右眼

            if (StringUtils.isBlank(recoInfo.getCjtphs())) {
                regInfoVO.setProcessTime(0); //采集图片耗时
            } else {
                regInfoVO.setProcessTime(Double.valueOf(recoInfo.getCjtphs())); //采集图片耗时
            }
            if (StringUtils.isBlank(recoInfo.getZy_xxzlpf())) {
                regInfoVO.setLeftEyeScore(0); //采集图片耗时
            } else {
                regInfoVO.setLeftEyeScore(Double.valueOf(recoInfo.getZy_xxzlpf())); //采集图片耗时
            }
            if (StringUtils.isBlank(recoInfo.getYy_xxzlpf())) {
                regInfoVO.setRightEyeScore(0); //采集图片耗时
            } else {
                regInfoVO.setRightEyeScore(Double.valueOf(recoInfo.getYy_xxzlpf())); //采集图片耗时
            }
            regInfoVO.setMessage(recoInfo.getCjbz()); //采集备注
            regInfoVO.setStatus(recoInfo.getSjzt());
            regInfoVO.setLocalStatus(recoInfo.getBdzt());
            regInfoVO.setCreateAt(recoInfo.getCreateAt());
            regInfoVO.setCjr_xm(recoInfo.getCjr_xm());
            regInfoVO.setSbbh(recoInfo.getSbbh());
            regInfoVO.setSbcsdm(recoInfo.getSbcsdm());
        } catch (Exception e) {
            String message = e.getMessage();
            log.error("[recoInfoToRegInfoVO] recoInfo={} regInfoVO={} errMsg={}", recoInfo, regInfoVO, e.toString());

        }
        return regInfoVO;
    }

    public static RegInfoVO checkInfoToRegInfoVO(CheckInfo checkInfo) {
        RegInfoVO regInfoVO = new RegInfoVO();
        regInfoVO.setId(checkInfo.getId());
        regInfoVO.setRegRealname(checkInfo.getBcjr_xm());//被采集人姓名
        regInfoVO.setRegCardno(checkInfo.getCjr_gmsfhm());//采集人身份证号
        regInfoVO.setRegFlag(checkInfo.getLrbz());//录入标志
        regInfoVO.setForceRegFlag(checkInfo.getQzcjbz());//强制采集标志
        regInfoVO.setAdminName(checkInfo.getCjr_xm()); //被采集人姓名
        regInfoVO.setCardImg(checkInfo.getZjzp());
        regInfoVO.setCardType(checkInfo.getBcjr_zjlxdm()); //被采集人证件类型代码
        regInfoVO.setCountry(checkInfo.getBcjr_gj()); //被采集人国家
        if (checkInfo.getZyqsqkdm() != null) {
            regInfoVO.setLeftEyeStatus(Integer.valueOf(checkInfo.getZyqsqkdm())); //左眼缺失情况代码
        }
        if (checkInfo.getYyqsqkdm() != null) {
            regInfoVO.setRightEyeStatus(Integer.valueOf(checkInfo.getYyqsqkdm())); //右眼缺失情况代码
        }

        if (checkInfo.getZyycjdm() != null) {
            regInfoVO.setBothEyeStatus(Integer.valueOf(checkInfo.getZyycjdm())); //左右眼采集代码
        }

        if (StringUtils.isBlank(checkInfo.getCjtphs())) {
            regInfoVO.setProcessTime(0); //采集图片耗时
        } else {
            regInfoVO.setProcessTime(Double.valueOf(checkInfo.getCjtphs())); //采集图片耗时
        }

        regInfoVO.setAdminInstitution(checkInfo.getUser_deptname());
//        regInfoVO.setSjly(checkInfo.getOffline()); //采集备注
        regInfoVO.setStatus(checkInfo.getSjzt());
        regInfoVO.setLocalStatus(checkInfo.getBdzt());
        regInfoVO.setInstitution(checkInfo.getUser_deptname()); //被采集人签发机关
        String sfbz = checkInfo.getSfbz();

        List<BzryBean> list = checkInfo.getBzry();
        if (CollUtil.isNotEmpty(list)) {
            BzryBean bzryBean = list.get(0);
            regInfoVO.setUserGroup(bzryBean.getRylb());
            regInfoVO.setCardImg(bzryBean.getZjzp());
        }

        regInfoVO.setCardno(checkInfo.getBcjr_zjhm()); //被采集人身份证号
        regInfoVO.setRegFlag(checkInfo.getLrbz());//录入标志
        regInfoVO.setRealname(checkInfo.getBcjr_xm()); //被采集人姓名
        regInfoVO.setSex(checkInfo.getBcjr_xb()); //被采集人性别
        regInfoVO.setNation(checkInfo.getBcjr_mz()); //被采集人民族

        regInfoVO.setBirthday(checkInfo.getBcjr_csrq()); //被采集人出生日期
        regInfoVO.setExpireAt(checkInfo.getBcjr_yxqx()); //有效期限
        regInfoVO.setHometown(checkInfo.getBcjr_hjdz()); //被采集人户籍地址
        regInfoVO.setHomeAddress(checkInfo.getBcjr_jzdz()); //被采集人居住地址
        regInfoVO.setSbbh(checkInfo.getSbbh());
        regInfoVO.setSbcsdm(checkInfo.getSbcsdm());
        return regInfoVO;
    }
}

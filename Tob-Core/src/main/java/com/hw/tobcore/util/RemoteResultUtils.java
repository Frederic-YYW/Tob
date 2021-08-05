package com.hw.tobcore.util;


import com.iristar.center.entity.ga.RecoBaseResult;
import com.iristar.center.entity.ga.RemoteResult;
import com.iristar.center.entity.syncremote.base.SyncInspectCheckBase;
import com.iristar.center.enums.BaseEnum;
import com.iristar.center.enums.RemoteResultEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class RemoteResultUtils {


    public static RemoteResult error(Integer status_code, String message, Object result, int elapsed_time, String request_id, String version) {
        return new RemoteResult(status_code+"", message, result, elapsed_time, request_id, version);
    }

    public static RemoteResult error(Integer status_code, String message, String request_id, String version) {
        return new RemoteResult(status_code+"", message, new HashMap<>(1), 50, request_id, version);
    }
    public static RemoteResult error(RemoteResultEnum resultEnum, String request_id, String version) {
        return new RemoteResult(resultEnum.getCode()+"", resultEnum.getMsg(), new HashMap<>(1), 50, request_id, version);
    }

    public static RemoteResult error(Integer status_code, String message, Object result) {
        return new RemoteResult(status_code+"", message, result, 50, null, null);
    }

    /**
     * 耗时、版本号、请求id在最终结束时赋值
     *
     * @param baseEnum
     * @return
     */
    public static RemoteResult error(BaseEnum<Integer, String> baseEnum) {
        return new RemoteResult(baseEnum.getCode()+"", baseEnum.getMsg(),new HashMap<>(1), 50, null, null);
    }

    /**
     * 耗时、版本号、请求id在最终结束时赋值
     *
     * @param code
     * @return
     */
    public static RemoteResult error(Integer code, String msg) {
        RecoBaseResult recoBaseResult = new RecoBaseResult();
        recoBaseResult.setBzry(0);
        recoBaseResult.setBzsl(0);
        //todo
//        recoBaseResult.setSfbz(Iris.MachEnum.NO_MATCH.getCode());
        recoBaseResult.setBzry(new ArrayList());
        return new RemoteResult(code+"", msg, recoBaseResult, 50, null, null);
    }

    /**
     * 成功
     *
     * @param status_code
     * @param message
     * @param result
     * @return
     */
    public static RemoteResult success(Integer status_code, String message, Object result) {
        return new RemoteResult(status_code+"", message, result, 50, null, null);
    }

    public static RemoteResult errorReg(Integer code, String msg) {
        return new RemoteResult(code+"", msg, JsonUtils.objectToJson(new HashMap<>()), 50, null, null);
    }


    /**
     * 获取核心识别结果
     *
     * @param sfbz
     * @param bzsl
     * @param resultList
     * @return
     */
    public static SyncInspectCheckBase getCoreResult(String sfbz, String bzsl, Object resultList) {
        SyncInspectCheckBase inspectCheckBase = new SyncInspectCheckBase();
        inspectCheckBase.setBzsl(bzsl);
        inspectCheckBase.setBzry(resultList);
        inspectCheckBase.setSfbz(sfbz);
        return inspectCheckBase;
    }
}

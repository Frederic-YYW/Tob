package com.hw.tobcore.exception;

import com.iristar.center.algorithm.AlgoException;
import com.iristar.center.entity.base.ResObject;
import com.iristar.center.enums.ResultEnum;
import com.iristar.center.util.ErrorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 全局异常处理中心
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2019/12/13 10:29
 */
@ControllerAdvice
@Slf4j
public class IristarException {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResObject exceptionGet(Exception e) {

        ResObject result = new ResObject();
        if (e instanceof DataAccessException) {
            DataAccessException exception = (DataAccessException) e;
            log.error(ErrorUtil.getStackTrace(e));
            log.error("[数据库操作错误] msg={}", exception.getMessage());
            result.failPack("数据库操作错误", ResultEnum.INTERNAL_SERVER_ERROR.getCode());
        } else if (e instanceof ClinetException) {
            ClinetException exception = (ClinetException) e;
            log.error(ErrorUtil.getStackTrace(e));
            log.error("[客户端错误] msg={}，code={}", exception.getMsg(), exception.getCode());
            result.failPack(exception.getMsg(), ResultEnum.PARAM_ERROR.getCode());
        } else if (e instanceof ServiceException) {
            ServiceException exception = (ServiceException) e;
            log.error(ErrorUtil.getStackTrace(e));
            log.error("[服务端错误] msg={},code={}", exception.getMsg(), exception.getCode());
            result.failPack(exception.getMsg(), exception.getCode());
        } else if (e instanceof ImageFormatException) {
            ServiceException exception = (ServiceException) e;
            log.error(ErrorUtil.getStackTrace(e));
            log.error("[图像处理异常] msg={},code={}", exception.getMsg(), exception.getCode());
            result.failPack("图像异常", ResultEnum.INTERNAL_SERVER_ERROR.getCode());
        } else if (e instanceof AlgoException) {
            log.error(ErrorUtil.getStackTrace(e));
            log.error("[算法处理异常] msg={},code={}", "算法处理异常", ResultEnum.INTERNAL_SERVER_ERROR.getCode());
            result.failPack("算法处理异常", ResultEnum.INTERNAL_SERVER_ERROR.getCode());
        } else if (e instanceof RemoteServerException) {
            RemoteServerException exception = (RemoteServerException) e;
            log.error(ErrorUtil.getStackTrace(e));
            log.error("[远程服务端异常] msg={},code={}", exception.getMsg(), exception.getCode());
            result.failPack("远程服务端异常", ResultEnum.ERROR.getCode());
        } else {
            log.error("未知异常 msg={}", ErrorUtil.getStackTrace(e));
            result.failPack("未知错误", 404);
        }
        return result;
    }
}

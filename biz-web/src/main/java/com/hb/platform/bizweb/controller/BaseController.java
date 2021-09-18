package com.hb.platform.bizweb.controller;

import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.common.ResultCode;
import com.hb.platform.hbcommon.standard.BusinessException;
import com.hb.platform.hbcommon.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller父类
 *
 * @version v0.1, 2021/8/22 22:54, create by huangbiao.
 */
@Slf4j
public class BaseController {

    /**
     * 请求
     */
    @Autowired
    protected HttpServletRequest request;

    /**
     * 响应
     */
    @Autowired
    protected HttpServletResponse response;

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result exception(BusinessException e) {
        String baseLog = LogUtils.getBaseLog("业务异常");
        log.error("{}\n{}", baseLog, LogUtils.getStackTrace(e));
        return Result.fail(e.getKey(), e.getMessage());
    }

    /**
     * 系统异常Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        String baseLog = LogUtils.getBaseLog("系统异常");
        log.error("{}\n{}", baseLog, LogUtils.getStackTrace(e));
        return Result.fail(ResultCode.ERROR);
    }

}

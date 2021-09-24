package com.hb.platform.hbbase.controller;

import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.common.ResultCode;
import com.hb.platform.hbbase.common.enums.ErrorProcessState;
import com.hb.platform.hbbase.common.enums.ErrorType;
import com.hb.platform.hbbase.container.Tools;
import com.hb.platform.hbcommon.standard.BusinessException;
import com.hb.platform.hbcommon.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    @Resource
    protected HttpServletRequest request;

    /**
     * 响应
     */
    @Resource
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
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        String baseLog = LogUtils.getBaseLog("系统异常");
        log.error("{}\n{}", baseLog, LogUtils.getStackTrace(e));
        Tools.errBoard().insert(ErrorType.BASE_SYSTEM_ERR, ErrorProcessState.WHTHOUT, e.getMessage(), null);
        return Result.fail(ResultCode.ERROR);
    }

}

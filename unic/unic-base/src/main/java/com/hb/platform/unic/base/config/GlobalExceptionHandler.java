package com.hb.platform.unic.base.config;

import com.hb.platform.unic.base.common.enums.BaseResCode;
import com.hb.platform.unic.base.model.Result;
import com.hb.platform.unic.common.standard.BusinessException;
import com.hb.platform.unic.common.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局的异常处理
 *
 * @version v0.1, 2020/7/24 15:07, create by huangbiao.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result exception(BusinessException e) {
        String baseLog = LogUtils.getBaseLog("业务异常");
        LOGGER.error("{}\n{}", baseLog, LogUtils.getStackTrace(e));
        return Result.fail(e.getKey(), e.getMessage());
    }

    /**
     * 系统异常Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        String baseLog = LogUtils.getBaseLog("系统异常");
        LOGGER.error("{}\n{}", baseLog, LogUtils.getStackTrace(e));
        return Result.fail(BaseResCode.ERROR);
    }

}

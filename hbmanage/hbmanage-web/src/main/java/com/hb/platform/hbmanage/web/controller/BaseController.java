package com.hb.platform.hbmanage.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller父类
 *
 * @version v0.1, 2021/8/22 22:54, create by huangbiao.
 */
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

}

    
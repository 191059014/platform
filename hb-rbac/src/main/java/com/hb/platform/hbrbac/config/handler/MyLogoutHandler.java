package com.hb.platform.hbrbac.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注销处理器
 *
 * @author Mr.Huang
 * @version v0.1, 2020/6/2 9:10, create by huangbiao.
 */
@Slf4j
public class MyLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("注销处理器={}", authentication);
    }

}

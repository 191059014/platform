package com.hb.platform.hbrbac.config.handler;

import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.util.ServletUtils;
import com.hb.platform.hbrbac.enums.RbacResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功处理器
 *
 * @author Mr.Huang
 * @version v0.1, 2020/6/2 9:10, create by huangbiao.
 */
@Slf4j
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        log.info("登陆成功={}", authentication);

        ServletUtils.writeJson(response, Result.fail(RbacResultCode.LOGIN_SUCCESS));
    }

}

package com.hb.platform.hbrbac.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token认证过滤器
 *
 * @version v0.1, 2021/4/18 1:33, create by huangbiao.
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        System.out.println("token认证过滤器：" + request.getMethod() + "===" + request.getRequestURI());

        // 放行
        filterChain.doFilter(request, response);
    }

}

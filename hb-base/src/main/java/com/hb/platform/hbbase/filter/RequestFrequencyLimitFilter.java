package com.hb.platform.hbbase.filter;

import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.common.ResultCode;
import com.hb.platform.hbbase.container.Tools;
import com.hb.platform.hbbase.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 请求频率限制，3秒钟达到10访问，意味着访问太频繁
 *
 * @version v0.1, 2021/9/19 23:00, create by huangbiao.
 */
@Slf4j
public class RequestFrequencyLimitFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
        String ipAddress = ServletUtils.getIpAddress(request);
        String key = "requestFrequencyLimit:" + ipAddress;
        String frequency = Tools.redis().opsForValue().get(key);
        log.debug("request frequency limit filter, ip={}, current times={}", ipAddress, frequency);
        if (StringUtils.isEmpty(frequency)) {
            Tools.redis().opsForValue().set(key, "0", 3, TimeUnit.SECONDS);
        } else {
            int times = Integer.parseInt(frequency);
            if (times > 10) {
                ServletUtils.writeJson(response, Result.fail(ResultCode.REQUEST_TOO_FREQUENTLY));
                return;
            } else {
                Tools.redis().opsForValue().increment(key);
            }
        }
        chain.doFilter(request, response);
    }

}

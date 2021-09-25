package com.hb.platform.bizweb.config.filter;

import com.hb.platform.bizweb.common.enums.ResponseEnum;
import com.hb.platform.bizweb.common.util.RedisKeyFactory;
import com.hb.unic.base.common.Result;
import com.hb.unic.cache.Redis;
import com.hb.unic.common.util.IpUtils;
import com.hb.unic.common.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
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
        String ipAddress = IpUtils.getIpAddress(request);
        String cacheKey = RedisKeyFactory.getRequestLimitCacheKey(ipAddress);
        Integer frequency = Redis.intOps().opsForValue().get(cacheKey);
        log.info("Uri: {}, Method: {}, Frequency: {}", request.getRequestURI(), request.getMethod(), frequency);
        if (frequency == null) {
            Redis.intOps().opsForValue().set(cacheKey, 1, 3, TimeUnit.SECONDS);
        } else {
            if (frequency > 10) {
                ServletUtils.writeJson(response, Result.fail(ResponseEnum.REQUEST_TOO_FREQUENTLY, frequency));
                return;
            } else {
                Redis.strOps().opsForValue().increment(cacheKey);
            }
        }
        chain.doFilter(request, response);
    }

}

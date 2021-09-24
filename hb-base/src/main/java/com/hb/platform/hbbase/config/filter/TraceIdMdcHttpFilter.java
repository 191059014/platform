package com.hb.platform.hbbase.config.filter;

import com.hb.platform.hbbase.common.constant.Consts;
import com.hb.platform.hbcommon.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * traceId的过滤器，mdc方式
 *
 * @author Mr.huang
 * @since 2020/4/21 9:07
 */
@Slf4j
public class TraceIdMdcHttpFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            String traceId = request.getHeader(Consts.TRACE_ID);
            if (traceId == null) {
                traceId = UuidUtils.uuidShort();
            }
            MDC.put(Consts.TRACE_ID, traceId);
            log.debug("traceId filter, traceId={}", traceId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(Consts.TRACE_ID);
        }
    }

}

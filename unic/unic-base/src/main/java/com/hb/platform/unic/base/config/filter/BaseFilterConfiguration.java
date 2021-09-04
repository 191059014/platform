package com.hb.platform.unic.base.config.filter;

import com.hb.platform.unic.base.common.util.FilterUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 拦截器
 *
 * @author huangbiao
 * @version v 0.1, 2020年12月6日 下午2:18:44, modify by huangbiao
 */
@Configuration
public class BaseFilterConfiguration {

    /**
     * traceId的mdc方式过滤器
     */
    @Bean
    public FilterRegistrationBean traceIdMdcHttpFilter() {
        return FilterUtils.build(new TraceIdMdcHttpFilter(), "traceIdMdcHttpFilter", -100, "/*");
    }

}

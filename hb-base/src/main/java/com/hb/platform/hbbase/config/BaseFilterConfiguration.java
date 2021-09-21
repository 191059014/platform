package com.hb.platform.hbbase.config;

import com.hb.platform.hbbase.common.util.FilterUtils;
import com.hb.platform.hbbase.config.filter.BlackListFilter;
import com.hb.platform.hbbase.config.filter.RequestFrequencyLimitFilter;
import com.hb.platform.hbbase.config.filter.TraceIdMdcHttpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 拦截器
 *
 * @author huangbiao
 * @version v 0.1, 2020年12月6日 下午2:18:44, modify by huangbiao
 */
@Configuration
public class BaseFilterConfiguration {

    /**
     * traceId的mdc方式
     */
    @Bean
    public FilterRegistrationBean traceIdMdcHttpFilter() {
        return FilterUtils.build(new TraceIdMdcHttpFilter(), "traceIdMdcHttpFilter", Ordered.HIGHEST_PRECEDENCE, "/*");
    }

    /**
     * 请求频率限制
     */
    @Bean
    public FilterRegistrationBean requestFrequencyLimitFilter() {
        return FilterUtils.build(new RequestFrequencyLimitFilter(), "requestFrequencyLimitFilter", -2000, "/*");
    }

    /**
     * 黑名单
     */
    @Bean
    public FilterRegistrationBean blackListFilter() {
        return FilterUtils.build(new BlackListFilter(), "blackListFilter", -1900, "/*");
    }

}

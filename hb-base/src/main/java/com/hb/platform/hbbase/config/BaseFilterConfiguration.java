package com.hb.platform.hbbase.config;

import com.hb.platform.hbbase.filter.BlackListFilter;
import com.hb.platform.hbbase.filter.RequestFrequencyLimitFilter;
import com.hb.platform.hbbase.filter.TraceIdMdcHttpFilter;
import com.hb.platform.hbbase.util.FilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 基础配置
     */
    @Autowired
    private BaseConfig baseConfig;

    /**
     * 黑名单
     */
    @Bean
    public FilterRegistrationBean blackListFilter() {
        return FilterUtils.build(new BlackListFilter(), "blackListFilter", -1100, "/*");
    }

    /**
     * traceId的mdc方式
     */
    @Bean
    public FilterRegistrationBean traceIdMdcHttpFilter() {
        return FilterUtils.build(new TraceIdMdcHttpFilter(), "traceIdMdcHttpFilter", -1000, "/*");
    }

    /**
     * 请求频率限制
     */
    @Bean
    public FilterRegistrationBean requestFrequencyLimitFilter() {
        return FilterUtils.build(new RequestFrequencyLimitFilter(), "requestFrequencyLimitFilter", -900, "/*");
    }

}

package com.hb.platform.bizweb.config;

import com.hb.platform.bizweb.config.filter.BlackListFilter;
import com.hb.platform.bizweb.config.filter.RequestFrequencyLimitFilter;
import com.hb.unic.common.util.FilterUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author huangbiao
 * @version v 0.1, 2020年12月6日 下午2:18:44, modify by huangbiao
 */
@Configuration
public class FilterConfiguration {

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

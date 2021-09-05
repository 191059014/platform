package com.hb.platform.hbmanage.web.config.filter;

import com.hb.platform.unic.base.util.FilterUtils;
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
     * 登录认证过滤器
     */
    @Bean
    public FilterRegistrationBean authFilterRegistration() {
        return FilterUtils.build(new AuthFilter(), "authFilter", 1, "/*");
    }

}

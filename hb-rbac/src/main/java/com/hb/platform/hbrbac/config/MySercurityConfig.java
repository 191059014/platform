package com.hb.platform.hbrbac.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * 安全配置
 *
 * @version v0.1, 2021/9/18 23:09, create by huangbiao.
 */
@Configuration
@ConfigurationProperties(prefix = "security")
@Data
public class MySercurityConfig {

    /**
     * 登录的url
     */
    private String loginUrl;

    /**
     * 忽略请求
     */
    private Set<String> ignoreUrls;

}

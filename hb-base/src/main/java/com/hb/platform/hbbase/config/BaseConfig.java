package com.hb.platform.hbbase.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 基础配置
 *
 * @version v0.1, 2021/9/18 23:09, create by huangbiao.
 */
@Configuration
@ConfigurationProperties(prefix = "base")
@Data
public class BaseConfig {

}

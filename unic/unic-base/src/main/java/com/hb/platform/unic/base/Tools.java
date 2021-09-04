package com.hb.platform.unic.base;

import com.hb.platform.unic.base.service.ICfgGlobalService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 对相关工具进行包装，方便输出
 *
 * @version v0.1, 2020/7/24 15:24, create by huangbiao.
 */
@Component
public class Tools implements InitializingBean {

    /**
     * Environment
     */
    @Autowired
    private Environment environment;
    private static Environment environmentAgent;

    /**
     * redis
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static StringRedisTemplate stringRedisTemplateAgent;

    /**
     * 全局配置服务
     */
    @Autowired
    private ICfgGlobalService cfgGlobalService;
    private static ICfgGlobalService cfgGlobalServiceAgent;

    @Override
    public void afterPropertiesSet() {
        cfgGlobalServiceAgent = cfgGlobalService;
        stringRedisTemplateAgent = stringRedisTemplate;
        environmentAgent = environment;
    }

    /**
     * 获取全局配置服务
     *
     * @return 结果
     */
    public static Environment env() {
        return environmentAgent;
    }

    /**
     * 获取全局配置服务
     *
     * @return 结果
     */
    public static StringRedisTemplate redis() {
        return stringRedisTemplateAgent;
    }

    /**
     * 获取全局配置服务
     *
     * @return 结果
     */
    public static ICfgGlobalService globalConfig() {
        return cfgGlobalServiceAgent;
    }

}

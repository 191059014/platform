package com.hb.platform.hbbase.container;

import com.hb.platform.hbbase.service.IGlobalConfigService;
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
     * string redis
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static StringRedisTemplate stringRedisTemplateAgent;

    /**
     * 全局配置
     */
    @Autowired
    private IGlobalConfigService globalConfigService;
    private static IGlobalConfigService globalConfigServiceAgent;

    @Override
    public void afterPropertiesSet() {
        environmentAgent = environment;
        stringRedisTemplateAgent = stringRedisTemplate;
        globalConfigServiceAgent = globalConfigService;
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
     * redis
     *
     * @return 结果
     */
    public static StringRedisTemplate redis() {
        return stringRedisTemplateAgent;
    }

    /**
     * 全局配置
     *
     * @return 结果
     */
    public static IGlobalConfigService globalConfig() {
        return globalConfigServiceAgent;
    }

}

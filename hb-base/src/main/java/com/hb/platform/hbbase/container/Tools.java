package com.hb.platform.hbbase.container;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
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
     * object redis
     */
    @Autowired
    private RedisTemplate<String, Object> objectRedisTemplate;
    private static RedisTemplate<String, Object> objectRedisTemplateAgent;

    /**
     * string redis
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static StringRedisTemplate stringRedisTemplateAgent;

    @Override
    public void afterPropertiesSet() {
        stringRedisTemplateAgent = stringRedisTemplate;
        environmentAgent = environment;
        objectRedisTemplateAgent = objectRedisTemplate;
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
     * redis
     *
     * @return 结果
     */
    public static RedisTemplate<String, Object> objectRedis() {
        return objectRedisTemplateAgent;
    }

}
package com.hb.platform.hbbase.container;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
     * Integer redis
     */
    @Autowired
    private RedisTemplate<String, Integer> integerRedisTemplate;
    private static RedisTemplate<String, Integer> integerRedisTemplateAgent;

    /**
     * Map redis
     */
    @Autowired
    private RedisTemplate<String, Map<String, Object>> mapRedisTemplate;
    private static RedisTemplate<String, Map<String, Object>> mapRedisTemplateAgent;

    /**
     * List redis
     */
    @Autowired
    private RedisTemplate<String, List<Object>> listRedisTemplate;
    private static RedisTemplate<String, List<Object>> listRedisTemplateAgent;

    /**
     * string redis
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static StringRedisTemplate stringRedisTemplateAgent;

    @Override
    public void afterPropertiesSet() {
        environmentAgent = environment;
        objectRedisTemplateAgent = objectRedisTemplate;
        integerRedisTemplateAgent = integerRedisTemplate;
        mapRedisTemplateAgent = mapRedisTemplate;
        listRedisTemplateAgent = listRedisTemplate;
        stringRedisTemplateAgent = stringRedisTemplate;
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

    /**
     * redis
     *
     * @return 结果
     */
    public static RedisTemplate<String, Integer> intRedis() {
        return integerRedisTemplateAgent;
    }

    /**
     * redis
     *
     * @return 结果
     */
    public static RedisTemplate<String, Map<String, Object>> mapRedis() {
        return mapRedisTemplateAgent;
    }

    /**
     * redis
     *
     * @return 结果
     */
    public static RedisTemplate<String, List<Object>> listRedis() {
        return listRedisTemplateAgent;
    }

}

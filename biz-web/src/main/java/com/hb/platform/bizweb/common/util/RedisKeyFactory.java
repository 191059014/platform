package com.hb.platform.bizweb.common.util;

import com.hb.platform.bizweb.common.constant.Const;
import com.hb.unic.cache.util.CacheUtils;
import org.springframework.util.StringUtils;

/**
 * redis缓存key管理工厂
 *
 * @author Mr.Huang
 * @version v0.1, RedisKeyFactory.java, 2020/6/19 15:29, create by huangbiao.
 */
public class RedisKeyFactory {

    /**
     * 获取token缓存键
     *
     * @param userId
     *            用户id
     * @return key
     */
    public static String getTokenKey(String userId) {
        return new StringBuilder(Const.PROJECT_NAME).append(":token:").append(userId).toString();
    }

    /**
     * 获取缓存key
     *
     * @param ip
     *            ip地址
     * @return 缓存key
     */
    public static String getRequestLimitCacheKey(String ip) {
        return CacheUtils.generateRedisKey("rateLimit", ip);
    }

    /**
     * 获取缓存key
     *
     * @param systemName
     *            系统名称
     * @param groupName
     *            分组名称
     * @param configKey
     *            键
     * @return 缓存key
     */
    public static String getGlobalConfigCacheKey(String systemName, String groupName, String configKey) {
        if (StringUtils.isEmpty(configKey)) {
            return CacheUtils.generateRedisKey(systemName, groupName);
        } else {
            return CacheUtils.generateRedisKey(systemName, groupName, configKey);
        }
    }

}

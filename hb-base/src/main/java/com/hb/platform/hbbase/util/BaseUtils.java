package com.hb.platform.hbbase.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 工具类
 *
 * @version v0.1, 2021/9/21 17:07, create by huangbiao.
 */
public class BaseUtils {

    /**
     * 项目名
     */
    private static final String PROJECT_NAME = "hbbase";

    /**
     * 获取缓存key
     *
     * @param ip
     *            ip地址
     * @return 缓存key
     */
    public static String getRequestLimitCacheKey(String ip) {
        return generateRedisKey(PROJECT_NAME, "requestLimit", ip);
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
        if (StringUtils.isNotBlank(configKey)) {
            return generateRedisKey(PROJECT_NAME, "globalConfig", systemName, groupName);
        } else {
            return generateRedisKey(PROJECT_NAME, "globalConfig", systemName, groupName, configKey);
        }
    }

    /**
     * 生成redis缓存键
     * 
     * @param arr
     *            一些需要拼接的参数
     * @return 缓存key
     */
    public static String generateRedisKey(String... arr) {
        return String.join(":", arr);
    }

}

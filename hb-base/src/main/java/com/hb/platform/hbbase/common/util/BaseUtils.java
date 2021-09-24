package com.hb.platform.hbbase.common.util;

import com.hb.platform.hbbase.common.constant.BaseConsts;
import org.apache.commons.lang3.StringUtils;

/**
 * 工具类
 *
 * @version v0.1, 2021/9/21 17:07, create by huangbiao.
 */
public class BaseUtils {

    /**
     * 获取缓存key
     *
     * @param ip
     *            ip地址
     * @return 缓存key
     */
    public static String getRequestLimitCacheKey(String ip) {
        return generateRedisKey(BaseConsts.PROJECT_NAME, "requestLimit", ip);
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
        if (StringUtils.isBlank(configKey)) {
            return generateRedisKey(systemName, groupName);
        } else {
            return generateRedisKey(systemName, groupName, configKey);
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

package com.hb.platform.bizweb.common.util;

import com.hb.platform.bizweb.common.constant.Const;

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

}

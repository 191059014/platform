package com.hb.platform.hbrbac.util;

import com.hb.platform.hbrbac.common.RbacConsts;

/**
 * 工具类
 *
 * @version v0.1, 2021/9/4 22:44, create by huangbiao.
 */
public class RbacUtils {

    /**
     * 判断是否是超级管理员
     * 
     * @param tenantId
     *            商户ID
     * @return 结果
     */
    public static boolean isSuperAdmin(long tenantId) {
        return 0L == tenantId;
    }

    /**
     * 获取当前登录用户的缓存key
     * 
     * @param userId
     *            用户id
     * @param username
     *            用户名
     * @return 缓存key
     */
    public static String getCurrentUserCacheKey(Object userId, String username) {
        return new StringBuilder().append(RbacConsts.PROJECT_NAME).append(":").append("currentUser").append(":")
            .append(userId).append("_").append(username).toString();
    }

}

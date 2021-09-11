package com.hb.platform.bizweb.common.util;

import com.hb.platform.bizweb.common.constant.Const;

/**
 * 工具类
 *
 * @version v0.1, 2021/9/4 22:44, create by huangbiao.
 */
public class HbmanageUtils {

    /**
     * 判断是否是超级管理员
     * 
     * @param tenantId
     *            商户ID
     * @return 结果
     */
    public static boolean isSuperAdmin(long tenantId) {
        return Const.SUPER_TENANTID == tenantId;
    }

}

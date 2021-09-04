package com.hb.platform.unic.rbac.container;

import com.hb.platform.unic.rbac.dao.dobj.SysUserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户上下文
 *
 * @version v0.1, 2021/9/4 11:22, create by huangbiao.
 */
public class RbacContext {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RbacContext.class);

    /**
     * 当前用户信息
     */
    private static final ThreadLocal<SysUserDO> TL = new ThreadLocal<>();

    /**
     * 将当前用户信息放入上下文
     * 
     * @param user
     *            用户信息
     */
    public static void set(SysUserDO user) {
        TL.set(user);
    }

    /**
     * 删除将当前用户信息
     *
     */
    public static void remove() {
        TL.remove();
    }

    /**
     * 获取当前用户
     *
     * @return 用户信息
     */
    public static SysUserDO getCurrentUser() {
        return TL.get();
    }

    /**
     * 获取当前用户商户ID
     * 
     * @return 商户ID
     */
    public static Long getCurrentTenantId() {
        return getCurrentUser().getTenantId();
    }

}

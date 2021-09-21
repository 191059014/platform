package com.hb.platform.hbrbac;

import com.hb.platform.hbbase.container.Tools;
import com.hb.platform.hbrbac.model.dobj.SysUserDO;
import com.hb.platform.hbrbac.util.RbacUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * spring security上下文
 *
 * @version v0.1, 2021/9/19 17:46, create by huangbiao.
 */
public class RbacContext {

    /**
     * 获取当前用户id
     * 
     * @return 用户id
     */
    public static long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    /**
     * 获取当前用户租户ID
     *
     * @return 租户ID
     */
    public static long getCurrentTenantId() {
        return getCurrentUser().getTenantId();
    }

    /**
     * 获取上下文中用户信息
     * 
     * @return 用户信息
     */
    public static SysUserDO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SysUserDO)authentication.getPrincipal();
    }

    /**
     * 清除用户信息
     */
    public static void clear() {
        SysUserDO currentUser = getCurrentUser();
        SecurityContextHolder.clearContext();
        Tools.redis().delete(RbacUtils.getCurrentUserCacheKey(currentUser.getId()));
    }

}

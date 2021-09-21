package com.hb.platform.hbrbac.config.handler;

import com.hb.platform.hbbase.container.Tools;
import com.hb.platform.hbrbac.model.dobj.SysUserDO;
import com.hb.platform.hbrbac.util.RbacUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注销处理器
 *
 * @author Mr.Huang
 * @version v0.1, 2020/6/2 9:10, create by huangbiao.
 */
@Slf4j
public class MyLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SysUserDO currentUser = (SysUserDO)authentication.getPrincipal();
        log.info("正在注销，当前用户={}", currentUser.getUserName());
        String currentUserCacheKey = RbacUtils.getCurrentUserCacheKey(currentUser.getId());
        Tools.redis().delete(currentUserCacheKey);
        log.info("删除缓存成功, key={}", currentUserCacheKey);
        SecurityContextHolder.clearContext();
        log.info("清除当前用户上下文成功");
    }

}

package com.hb.platform.hbrbac.config.handler;

import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.container.Tools;
import com.hb.platform.hbbase.util.ServletUtils;
import com.hb.platform.hbcommon.security.AES;
import com.hb.platform.hbcommon.security.Base64;
import com.hb.platform.hbrbac.common.RbacConsts;
import com.hb.platform.hbrbac.config.MyUserDetails;
import com.hb.platform.hbrbac.enums.RbacResultCode;
import com.hb.platform.hbrbac.model.dobj.SysUserDO;
import com.hb.platform.hbrbac.model.dto.UserCache;
import com.hb.platform.hbrbac.util.RbacUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 登陆成功处理器
 *
 * @author Mr.Huang
 * @version v0.1, 2020/6/2 9:10, create by huangbiao.
 */
@Slf4j
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        log.info("登陆成功，当前用户={}", userDetails.getUsername());
        SysUserDO user = userDetails.getUser();
        /*
         * 先用aes加密，再用base64加密
         */
        String aesEncode = AES.encode(user.getId() + "_" + user.getUserName(), RbacConsts.PROJECT_NAME);
        String base64Encode = Base64.encode(aesEncode);
        response.setHeader("Authorization", base64Encode);
        /*
         * 用户信息放入缓存
         */
        String ipAddress = ServletUtils.getIpAddress(request);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Set<String> permissionSet =
            authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        UserCache userCache = new UserCache(ipAddress, user, permissionSet);
        String currentUserCacheKey = RbacUtils.getCurrentUserCacheKey(user.getId(), user.getUserName());
        Tools.objectRedis().opsForValue().set(currentUserCacheKey, userCache, RbacConsts.MINUTE_30,
            TimeUnit.MILLISECONDS);
        log.info("将用户放入缓存成功, key={}", currentUserCacheKey);
        ServletUtils.writeJson(response, Result.of(RbacResultCode.LOGIN_SUCCESS, user.getUserName()));
    }

}
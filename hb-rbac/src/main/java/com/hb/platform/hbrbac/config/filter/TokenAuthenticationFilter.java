package com.hb.platform.hbrbac.config.filter;

import com.alibaba.fastjson.JSON;
import com.hb.platform.hbbase.common.constant.Consts;
import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.container.Tools;
import com.hb.platform.hbbase.util.ServletUtils;
import com.hb.platform.hbcommon.security.AES;
import com.hb.platform.hbcommon.security.Base64;
import com.hb.platform.hbrbac.common.RbacConsts;
import com.hb.platform.hbrbac.config.MySercurityConfig;
import com.hb.platform.hbrbac.enums.RbacResultCode;
import com.hb.platform.hbrbac.model.dto.UserCache;
import com.hb.platform.hbrbac.util.RbacUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * token认证过滤器
 *
 * @version v0.1, 2021/4/18 1:33, create by huangbiao.
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 配置
     */
    private MySercurityConfig mySercurityConfig;

    public TokenAuthenticationFilter(MySercurityConfig mySercurityConfig) {
        this.mySercurityConfig = mySercurityConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
        /*
         * 登录请求，直接放行
         */
        if (request.getRequestURI().equals(mySercurityConfig.getLoginUrl())) {
            chain.doFilter(request, response);
            return;
        }
        /*
         * 校验token
         */
        String authorization = request.getHeader("Authorization");
        log.info("authorization from request: {}", authorization);
        if (StringUtils.isEmpty(authorization)) {
            ServletUtils.writeJson(response, Result.fail(RbacResultCode.TOKEN_NULL));
            return;
        }
        if (!authorization.startsWith(RbacConsts.BEARER)) {
            ServletUtils.writeJson(response, Result.fail(RbacResultCode.TOKEN_ILLEGAL));
            return;
        }
        /*
         * 解密token
         */
        String base64Encode = authorization.replace(RbacConsts.BEARER, "");
        String base64Decode = Base64.decode(base64Encode);
        String aesDecode = AES.decode(base64Decode, RbacConsts.PROJECT_NAME);
        /*
         * 从缓存中获取当前用户信息
         */
        String currentUserCacheKey = RbacUtils.getCurrentUserCacheKey(aesDecode);
        log.info("current user cache key: {}", currentUserCacheKey);
        String userCacheJson = Tools.redis().opsForValue().get(currentUserCacheKey);
        if (StringUtils.isEmpty(userCacheJson)) {
            ServletUtils.writeJson(response, Result.fail(RbacResultCode.TOKEN_EXPIRED));
            return;
        }
        UserCache userCache = JSON.parseObject(userCacheJson, UserCache.class);
        /*
         * 校验访问ip，防止token被截取
         */
        String ipAddress = ServletUtils.getIpAddress(request);
        if (!ipAddress.equals(userCache.getLastLoginIp())) {
            ServletUtils.writeJson(response, Result.fail(RbacResultCode.IP_CHANGE));
            return;
        }
        /*
         * token续航
         */
        Long remainExpire = Tools.redis().getExpire(currentUserCacheKey, TimeUnit.SECONDS);
        if (remainExpire < Consts.MINUTE_10) {
            Tools.redis().expire(currentUserCacheKey, Consts.MINUTE_30, TimeUnit.SECONDS);
        }
        /*
         * 将用户信息放入spring security上下文，如果不设置，则视为匿名访问
         */
        Set<String> authorities = userCache.getAuthorities();
        List<GrantedAuthority> list = new ArrayList<>();
        authorities.forEach(permission -> list.add(new SimpleGrantedAuthority(permission)));
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userCache.getUser(), userCache.getUser().getPassword(), list);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        /*
         * 最终放行
         */
        chain.doFilter(request, response);
    }

}

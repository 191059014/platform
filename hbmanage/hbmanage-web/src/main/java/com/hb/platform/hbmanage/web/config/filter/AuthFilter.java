package com.hb.platform.hbmanage.web.config.filter;

import com.alibaba.fastjson.JSON;
import com.hb.platform.hbmanage.web.common.constant.Const;
import com.hb.platform.hbmanage.web.common.enums.ResultCode;
import com.hb.platform.hbmanage.web.common.util.RedisKeyFactory;
import com.hb.platform.hbmanage.web.container.Tools;
import com.hb.platform.unic.base.util.ServletUtils;
import com.hb.platform.hbmanage.web.common.Result;
import com.hb.platform.unic.common.constant.CommonConsts;
import com.hb.platform.unic.common.security.Base64Encrypt;
import com.hb.platform.unic.common.security.SymmetricEncrypt;
import com.hb.platform.hbmanage.web.container.RbacContext;
import com.hb.platform.unic.rbac.dobj.SysUserDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证过滤器
 *
 * @author Mr.huang
 * @since 2020/4/21 9:07
 */
public class AuthFilter extends OncePerRequestFilter {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    /**
     * Bearer
     */
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        String requestUri = request.getRequestURI();
        LOGGER.info("auth filter, request uri={}", requestUri);
        String ignoreUrlPatterns = Tools.env().getProperty("security.ignoreUrlPatterns");
        if (ServletUtils.matchUri(requestUri, ignoreUrlPatterns.split(";"))) {
            chain.doFilter(request, response);
            return;
        }
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith(BEARER)) {
            ServletUtils.writeResponse(response, JSON.toJSONString(Result.fail(ResultCode.AUTH_FAIL)));
            return;
        }
        String token = authorization.substring(authorization.indexOf(BEARER) + BEARER.length());
        // Base64解码
        String aesEncode = Base64Encrypt.JDK.decode(token, CommonConsts.UTF_8);
        // AES解密
        String userId = SymmetricEncrypt.AES.decode(aesEncode, Const.PROJECT_NAME);
        String tokenKey = RedisKeyFactory.getTokenKey(userId);
        String userJson = Tools.redis().opsForValue().get(tokenKey);
        LOGGER.info("缓存key={}, 当前用户信息={}", tokenKey, userJson);
        if (StringUtils.isBlank(userJson)) {
            ServletUtils.writeResponse(response, JSON.toJSONString(Result.fail(ResultCode.TOKEN_EXPIRE)));
            return;
        }
        SysUserDO currentUser = JSON.parseObject(userJson, SysUserDO.class);
        // 讲用户信息放入上下文
        RbacContext.set(currentUser);
        try {
            chain.doFilter(request, response);
        } finally {
            RbacContext.remove();
        }
    }

}

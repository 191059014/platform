package com.hb.platform.bizweb.config.filter;

import com.alibaba.fastjson.JSON;
import com.hb.platform.bizweb.common.enums.ResponseEnum;
import com.hb.unic.base.common.Result;
import com.hb.unic.common.util.IpUtils;
import com.hb.unic.common.util.ServletUtils;
import com.hb.unic.config.ConfigContext;
import com.hb.unic.config.dao.dobj.GlobalConfigDO;
import com.hb.unic.config.enums.GlobalConfigKey;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 黑名单拦截
 *
 * @version v0.1, 2021/9/21 16:36, create by huangbiao.
 */
public class BlackListFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
        String ipAddress = IpUtils.getIpAddress(request);
        GlobalConfigDO config = ConfigContext.globalCfg().getFromCache(GlobalConfigKey.BLACK_LIST.getSystemName(),
            GlobalConfigKey.BLACK_LIST.getGroupName(), GlobalConfigKey.BLACK_LIST.getConfigKey());
        if (config != null) {
            List<String> blackList = JSON.parseArray(config.getConfigValue(), String.class);
            if (blackList.contains(ipAddress)) {
                ServletUtils.writeJson(response, Result.fail(ResponseEnum.BLACK_LIST_IP, ipAddress));
            }
        } else {
            chain.doFilter(request, response);
        }

    }

}

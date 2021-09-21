package com.hb.platform.hbbase.filter;

import com.alibaba.fastjson.JSON;
import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.common.ResultCode;
import com.hb.platform.hbbase.common.enums.GlobalConfigKey;
import com.hb.platform.hbbase.container.Tools;
import com.hb.platform.hbbase.dao.dobj.GlobalConfigDO;
import com.hb.platform.hbbase.util.ServletUtils;
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
        String ipAddress = ServletUtils.getIpAddress(request);
        GlobalConfigDO config = Tools.globalConfig().getFromCache(GlobalConfigKey.BLACK_LIST.getSystemName(),
            GlobalConfigKey.BLACK_LIST.getGroupName(), GlobalConfigKey.BLACK_LIST.getConfigKey());
        if (config != null) {
            List<String> blackList = JSON.parseArray(config.getConfigValue(), String.class);
            if (blackList.contains(ipAddress)) {
                ServletUtils.writeJson(response, Result.fail(ResultCode.BLACK_LIST_IP, ipAddress));
            }
        } else {
            chain.doFilter(request, response);
        }

    }

}

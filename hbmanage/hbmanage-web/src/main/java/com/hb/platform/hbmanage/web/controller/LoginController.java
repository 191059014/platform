package com.hb.platform.hbmanage.web.controller;

import com.alibaba.fastjson.JSON;
import com.hb.platform.hbmanage.web.common.constant.Const;
import com.hb.platform.hbmanage.web.common.util.RedisKeyFactory;
import com.hb.platform.unic.base.Tools;
import com.hb.platform.unic.base.annotation.InOutLog;
import com.hb.platform.unic.base.model.Result;
import com.hb.platform.unic.common.constant.CommonConsts;
import com.hb.platform.unic.common.security.Base64Encrypt;
import com.hb.platform.unic.common.security.SingleTrackEncrypt;
import com.hb.platform.unic.common.security.SymmetricEncrypt;
import com.hb.platform.unic.common.validator.Assert;
import com.hb.platform.unic.rbac.common.enums.RbacResCode;
import com.hb.platform.unic.rbac.dao.dobj.SysUserDO;
import com.hb.platform.unic.rbac.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录
 *
 * @version v0.1, 2021/9/4 10:33, create by huangbiao.
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * 用户服务
     */
    @Autowired
    private ISysUserService userService;

    /**
     * 登录
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @return 结果
     */
    @InOutLog("登录")
    @GetMapping("/doLogin")
    public Result<Map<String, Object>> doLogin(@RequestParam("username") String username,
        @RequestParam("password") String password) {
        Assert.hasText(username, RbacResCode.USERNAME_NULL);
        Assert.hasText(password, RbacResCode.PASSWORD_NULL);
        String encodePassword = SingleTrackEncrypt.MD5.encode(password, CommonConsts.UTF_8);
        SysUserDO query = new SysUserDO();
        query.setUserName(username);
        query.setPassword(encodePassword);
        SysUserDO sysUser = userService.selectOne(query);
        Assert.notNull(sysUser, RbacResCode.USER_NOT_EXIST);
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        // AES加密
        String aesEncode = SymmetricEncrypt.AES.encode(sysUser.getId().toString(), Const.PROJECT_NAME);
        // Base64编码
        String token = Base64Encrypt.JDK.encode(aesEncode, CommonConsts.UTF_8);
        map.put("token", token);
        // 放入缓存
        String userJson = JSON.toJSONString(sysUser);
        String tokenKey = RedisKeyFactory.getTokenKey(sysUser.getId().toString());
        Tools.redis().opsForValue().set(tokenKey, userJson, CommonConsts.MINUTE_30, TimeUnit.SECONDS);
        LOGGER.info("token放入redis成功, key={}, value={}", tokenKey, userJson);
        return Result.success(map);
    }

}

package com.hb.platform.hbrbac.model.dto;

import com.hb.platform.hbrbac.model.dobj.SysUserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 用户缓存信息
 *
 * @version v0.1, 2021/9/19 15:48, create by huangbiao.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCache {

    /**
     * 最后一次登录的ip
     */
    private String lastLoginIp;

    /**
     * 用户信息
     */
    private SysUserDO user;

    /**
     * 权限集合
     */
    private Set<String> authorities;

}

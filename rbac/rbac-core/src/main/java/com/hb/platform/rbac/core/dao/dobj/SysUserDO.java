package com.hb.platform.rbac.core.dao.dobj;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hb.platform.unic.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息表数据模型
 *
 * @version v0.1, 2021-08-23 23:22:00, create by Mr.Huang.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysUserDO extends BaseDO {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

}

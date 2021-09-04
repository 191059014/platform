package com.hb.platform.unic.rbac.common.enums;

import com.hb.platform.unic.common.standard.IErrorCode;

/**
 * 返回码
 *
 * @version v0.1, 2021/8/22 18:42, create by huangbiao.
 */
public enum RbacResCode implements IErrorCode {

    USERNAME_NULL("10100", "用户名不能为空"),
    PASSWORD_NULL("10101", "密码不能为空"),
    USER_NOT_EXIST("10102", "用户名或密码错误"),
    AUTH_FAIL("10105", "认证失败，请重新登录"),
    TOKEN_EXPIRE("10106", "会话失效，请重新登录"),
    ;

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应信息
     */
    private String msg;

    RbacResCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

}

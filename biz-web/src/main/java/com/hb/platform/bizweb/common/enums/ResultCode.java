package com.hb.platform.bizweb.common.enums;

import com.hb.platform.hbcommon.standard.IErrorCode;

/**
 * 返回码
 *
 * @version v0.1, 2021/8/22 18:42, create by huangbiao.
 */
public enum ResultCode implements IErrorCode {

    SUCCESS("10000", "成功"),
    ERROR("10001", "系统异常，请稍后再试！"),
    PARAM_ILLEGAL("10002", "参数非法"),
    PAGE_PARAM_ERROR("10003", "分页参数错误"),

    USERNAME_NULL("10100", "用户名不能为空"),
    PASSWORD_NULL("10101", "密码不能为空"),
    USER_NOT_EXIST("10102", "用户名或密码错误"),
    AUTH_FAIL("10105", "认证失败，请重新登录"),
    TOKEN_EXPIRE("10106", "会话失效，请重新登录"),

    NO_DATA("20000", "无数据"),
    ;

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应信息
     */
    private String msg;

    ResultCode(String code, String msg) {
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

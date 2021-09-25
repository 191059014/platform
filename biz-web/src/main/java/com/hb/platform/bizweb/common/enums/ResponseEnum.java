package com.hb.platform.bizweb.common.enums;

import com.hb.unic.common.standard.IErrorCode;

/**
 * 返回码
 *
 * @version v0.1, 2021/8/22 18:42, create by huangbiao.
 */
public enum ResponseEnum implements IErrorCode {

    SUCCESS("10000", "成功"),
    ERROR("10001", "系统异常，请稍后再试！"),
    FAIL("10002", "操作失败"),
    NO_DATA("10003", "无数据"),
    PARAM_ILLEGAL("10004", "参数非法"),
    PAGE_PARAM_ERROR("10005", "分页参数错误"),
    REQUEST_TOO_FREQUENTLY("10006", "访问太频繁"),
    BLACK_LIST_IP("10007", "您已经被列入黑名单，无法访问"),
    RECORD_REPEAT("10008", "已存在唯一性相同的记录"),
    ;

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应信息
     */
    private String msg;

    ResponseEnum(String code, String msg) {
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

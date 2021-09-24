package com.hb.platform.hbbase.common.enums;

import com.hb.platform.hbbase.common.constant.BaseConsts;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 异常看板
 *
 * @version v0.1, 2021/9/23 22:40, create by huangbiao.
 */
@Getter
public enum ErrorType {

    SYSTEM_ERR(BaseConsts.PROJECT_NAME, "基础平台", "system_exception", "系统异常");

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 系统名称描述
     */
    private String systemNameDesc;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务类型描述
     */
    private String bizTypeDesc;

    ErrorType(String systemName, String systemNameDesc, String bizType, String bizTypeDesc) {
        this.systemName = systemName;
        this.systemNameDesc = systemNameDesc;
        this.bizType = bizType;
        this.bizTypeDesc = bizTypeDesc;
    }

    /**
     * 通过系统名称查询
     * 
     * @param systemName
     *            系统名称
     * @return 结果
     */
    public static List<ErrorType> getBySystemName(String systemName) {
        return Arrays.stream(ErrorType.values()).filter(err -> err.getSystemName().equals(systemName))
            .collect(Collectors.toList());
    }

}

package com.hb.platform.hbbase.dao.dobj;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hb.platform.hbbase.dao.dobj.base.impl.AbstractBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局配置表数据模型
 *
 * @version v0.1, 2021-08-23 22:44:04, create by Mr.Huang.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CfgGlobalDO extends AbstractBaseDO {

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 配置标识
     */
    private String configKey;

    /**
     * 配置内容
     */
    private String configValue;

    /**
     * 备注
     */
    private String remark;

}

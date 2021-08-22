package com.hb.platform.configcenter.core.dao.dobj;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hb.platform.unic.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局配置表数据模型
 *
 * @version v0.1, 2021-08-22 20:39:39, create by Mr.Huang.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GlobalConfigDO extends BaseDO {

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

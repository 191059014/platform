package com.hb.platform.rbac.core.dao.dobj;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.hb.platform.unic.model.BaseDO;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hb.platform.unic.common.util.DateUtils;

/**
 * 权限信息表数据模型
 *
 * @version v0.1, 2021-08-23 23:06:14, create by Mr.Huang.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysPermissionDO extends BaseDO {

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 资源类型：folder-目录，page-页面，button-按钮
     */
    private String resourceType;

    /**
     * 权限值
     */
    private String permissionValue;

    /**
     * 图标
     */
    private String icon;

    /**
     * 链接
     */
    private String url;

}

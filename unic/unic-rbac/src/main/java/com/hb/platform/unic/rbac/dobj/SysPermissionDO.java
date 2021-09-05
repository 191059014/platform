package com.hb.platform.unic.rbac.dobj;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.hb.platform.unic.base.model.BaseDO;

/**
 * 权限信息表数据模型
 *
 * @version v0.1, 2021-09-04 12:48:40, create by Mr.Huang.
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

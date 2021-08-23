package com.hb.platform.rbac.core.dao.dobj;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hb.platform.unic.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色权限关系表数据模型
 *
 * @version v0.1, 2021-08-23 23:21:57, create by Mr.Huang.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysRolePermissionDO extends BaseDO {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限ID
     */
    private Long permissionId;

}

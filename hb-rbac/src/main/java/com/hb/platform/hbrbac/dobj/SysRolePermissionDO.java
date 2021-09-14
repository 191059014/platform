package com.hb.platform.hbrbac.dobj;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hb.platform.hbbase.dao.dobj.base.impl.AbstractBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色权限关系表数据模型
 *
 * @version v0.1, 2021-09-04 12:48:44, create by Mr.Huang.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysRolePermissionDO extends AbstractBaseDO {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限ID
     */
    private Long permissionId;

}

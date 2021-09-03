package com.hb.platform.unic.rbac.dao.dobj;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hb.platform.unic.base.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关系表数据模型
 *
 * @version v0.1, 2021-08-23 23:22:03, create by Mr.Huang.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysUserRoleDO extends BaseDO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

}

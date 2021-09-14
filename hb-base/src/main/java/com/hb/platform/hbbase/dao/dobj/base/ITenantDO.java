package com.hb.platform.hbbase.dao.dobj.base;

/**
 * 多租户信息
 *
 * @version v0.1, 2020/9/16 9:44, create by huangbiao.
 */
public interface ITenantDO {

    /**
     * 获取多租户ID
     */
    Long getTenantId();

    /**
     * 设置多租户ID
     */
    void setTenantId(Long tenantId);

}

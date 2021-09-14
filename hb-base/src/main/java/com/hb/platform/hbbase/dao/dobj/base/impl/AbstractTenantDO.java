package com.hb.platform.hbbase.dao.dobj.base.impl;

import com.hb.platform.hbbase.dao.dobj.base.IBaseDO;
import com.hb.platform.hbbase.dao.dobj.base.ITenantDO;

/**
 * 数据模型超类
 *
 * @version v0.1, 2021-09-12 13:24:32, create by Mr.Huang.
 */
public abstract class AbstractTenantDO extends AbstractBaseDO implements IBaseDO, ITenantDO {

    /**
     * 商户ID
     */
    private Long tenantId;

    @Override
    public Long getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

}

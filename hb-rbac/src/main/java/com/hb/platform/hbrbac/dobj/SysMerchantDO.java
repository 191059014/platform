package com.hb.platform.hbrbac.dobj;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.hb.platform.hbbase.model.BaseDO;

/**
 * 商户表数据模型
 *
 * @version v0.1, 2021-09-04 12:48:33, create by Mr.Huang.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysMerchantDO extends BaseDO {

    /**
     * 商户名称
     */
    private String merchantName;

}

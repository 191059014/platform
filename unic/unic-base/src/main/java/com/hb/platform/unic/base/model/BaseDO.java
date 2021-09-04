package com.hb.platform.unic.base.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hb.platform.unic.common.util.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * 数据模型超类
 *
 * @version v0.1, 2021-08-22 13:24:32, create by Mr.Huang.
 */
@Data
public class BaseDO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtils.FORMAT_DEFAULT, timezone = DateUtils.TIME_ZONE_DEFAULT)
    private Date createTime;

    /**
     * 更新人
     */
    private String updator;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DateUtils.FORMAT_DEFAULT, timezone = DateUtils.TIME_ZONE_DEFAULT)
    private Date updateTime;

    /**
     * 记录有效状态
     */
    private Integer isValid;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 商户ID
     */
    private Long tenantId;

}

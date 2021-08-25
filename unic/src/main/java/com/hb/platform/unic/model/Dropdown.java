package com.hb.platform.unic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 下拉框
 *
 * @version v0.1, 2021/8/24 21:55, create by huangbiao.
 */
@Data
@AllArgsConstructor
public class Dropdown {

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private Object value;

}

    
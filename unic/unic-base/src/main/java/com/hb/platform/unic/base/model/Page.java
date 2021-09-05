package com.hb.platform.unic.base.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 分页
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Page<T> {

    /**
     * 总条数
     */
    private Long count;

    /**
     * 数据集
     */
    private List<T> data;

    /**
     * 创建Page对象
     *
     * @param count
     *            总条数
     * @param data
     *            数据集
     * @param <T>
     *            泛型
     * @return 分页结果
     */
    public static <T> Page<T> create(Long count, List<T> data) {
        Page<T> page = new Page<>();
        page.setCount(count);
        page.setData(data);
        return page;
    }

}

package com.hb.platform.unic.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hb.platform.unic.common.enums.ResultCode;
import com.hb.platform.unic.common.exception.BusinessException;
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
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 分页开始位置
     */
    private Integer startIndex;

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
     * @param pageNum  当前第几页
     * @param pageSize 每页条数
     * @return 分页条件
     */
    public static Page createBefore(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1 || pageSize == null || pageSize < 1) {
            throw new BusinessException(ResultCode.PAGE_PARAM_ERROR);
        }
        Page page = new Page();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setStartIndex((pageNum - 1) * pageSize);
        return page;
    }

    /**
     * 创建Page对象
     *
     * @param count 总条数
     * @param data  数据集
     * @param <T>   泛型
     * @return 分页结果
     */
    public static <T> Page<T> createAfter(Long count, List<T> data) {
        Page<T> page = new Page<>();
        page.setCount(count);
        page.setData(data);
        return page;
    }

}

package com.hb.platform.hbbase.service;

import java.util.List;
import java.util.Set;

import com.hb.platform.hbbase.dao.dobj.GlobalConfigDO;
import com.hb.platform.hbbase.model.Page;

/**
 * 全局配置表服务层接口
 *
 * @version v0.1, 2021-09-21 15:08:19, create by Mr.Huang.
 */
public interface IGlobalConfigService {

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    GlobalConfigDO selectById(Long id);

    /**
     * 通过条件查询单条数据
     *
     * @param globalConfig
     *            查询条件
     * @return 实例对象
     */
    GlobalConfigDO selectOne(GlobalConfigDO globalConfig);

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param globalConfig
     *            查询条件
     * @return 对象列表
     */
    List<GlobalConfigDO> selectList(GlobalConfigDO globalConfig);

    /**
     * 分页查询数据
     *
     * @param globalConfig
     *            查询条件
     * @param pageNum
     *            当前页数
     * @param pageSize
     *            每页查询条数
     * @return 对象列表
     */
    Page<GlobalConfigDO> selectPages(GlobalConfigDO globalConfig, int pageNum, int pageSize);

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param globalConfig
     *            查询条件
     * @return 结果集
     */
    List<GlobalConfigDO> selectByIdSet(Set<Long> idSet, GlobalConfigDO globalConfig);


    /**
     * 新增
     *
     * @param globalConfig
     *            实例对象
     * @return 影响行数
     */
    int insert(GlobalConfigDO globalConfig);

    /**
     * 通过主键修改
     *
     * @param globalConfig
     *            实例对象
     * @return 影响行数
     */
    int updateById(GlobalConfigDO globalConfig);

    /**
     * 通过主键删除数据
     *
     * @param globalConfig
     *            实例对象
     * @return 影响行数
     */
    int deleteById(GlobalConfigDO globalConfig);

}

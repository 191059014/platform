package com.hb.platform.configcenter.core.service;

import java.util.List;
import java.util.Set;

import com.hb.platform.configcenter.core.dao.dobj.CfgGlobalDO;
import com.hb.platform.unic.model.Page;

/**
 * 全局配置表服务层接口
 *
 * @version v0.1, 2021-08-23 22:44:04, create by Mr.Huang.
 */
public interface ICfgGlobalService {

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    CfgGlobalDO selectById(Long id);

    /**
     * 通过条件查询单条数据
     *
     * @param cfgGlobal
     *            查询条件
     * @return 实例对象
     */
    CfgGlobalDO selectOne(CfgGlobalDO cfgGlobal);

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param cfgGlobal
     *            查询条件
     * @return 对象列表
     */
    List<CfgGlobalDO> selectList(CfgGlobalDO cfgGlobal);

    /**
     * 分页查询数据
     *
     * @param cfgGlobal
     *            查询条件
     * @param pageNum
     *            当前页数
     * @param pageSize
     *            每页查询条数
     * @return 对象列表
     */
    Page<CfgGlobalDO> selectPages(CfgGlobalDO cfgGlobal, int pageNum, int pageSize);

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param cfgGlobal
     *            查询条件
     * @return 结果集
     */
    List<CfgGlobalDO> selectByIdSet(Set<Long> idSet, CfgGlobalDO cfgGlobal);


    /**
     * 新增
     *
     * @param cfgGlobal
     *            实例对象
     * @return 影响行数
     */
    int insert(CfgGlobalDO cfgGlobal);

    /**
     * 通过主键修改
     *
     * @param cfgGlobal
     *            实例对象
     * @return 影响行数
     */
    int updateById(CfgGlobalDO cfgGlobal);

    /**
     * 通过主键删除数据
     *
     * @param id
     *            主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

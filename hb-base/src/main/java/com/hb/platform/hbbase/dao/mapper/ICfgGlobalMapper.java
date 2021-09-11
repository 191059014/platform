package com.hb.platform.hbbase.dao.mapper;

import java.util.List;
import java.util.Set;

import com.hb.platform.hbbase.dao.dobj.CfgGlobalDO;
import org.apache.ibatis.annotations.Param;
import com.hb.platform.hbbase.model.Page;

/**
 * 全局配置表数据库层接口
 *
 * @version v0.1, 2021-08-23 22:44:04, create by Mr.Huang.
 */
public interface ICfgGlobalMapper {

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    CfgGlobalDO selectById(Long id);

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param cfgGlobal
     *            查询条件
     * @return 对象列表
     */
    List<CfgGlobalDO> selectList(@Param("qc") CfgGlobalDO cfgGlobal);

    /**
     * 条件查询总条数
     *
     * @param cfgGlobal
     *            查询条件
     * @return 总条数
     */
    Long selectCount(@Param("qc") CfgGlobalDO cfgGlobal);

    /**
     * 查询指定行数据
     *
     * @param cfgGlobal
     *            查询条件
     * @param page
     *            分页条件
     * @return 对象列表
     */
    List<CfgGlobalDO> selectPages(@Param("qc") CfgGlobalDO cfgGlobal, @Param("pc") Page page);

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param cfgGlobal
     *            查询条件
     * @return 结果集
     */
    List<CfgGlobalDO> selectByIdSet(@Param("idSet") Set<Long> idSet, @Param("qc") CfgGlobalDO cfgGlobal);

    /**
     * 新增
     *
     * @param cfgGlobal
     *            实例对象
     * @return 影响行数
     */
    int insert(@Param("qc") CfgGlobalDO cfgGlobal);

    /**
     * 通过主键修改
     *
     * @param cfgGlobal
     *            实例对象
     * @return 影响行数
     */
    int updateById(@Param("qc") CfgGlobalDO cfgGlobal);

    /**
     * 通过主键删除数据
     *
     * @param id
     *            主键
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

}

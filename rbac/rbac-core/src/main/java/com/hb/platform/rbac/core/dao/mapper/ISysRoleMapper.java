package com.hb.platform.rbac.core.dao.mapper;

import java.util.List;
import java.util.Set;

import com.hb.platform.rbac.core.dao.dobj.SysRoleDO;
import org.apache.ibatis.annotations.Param;
import com.hb.platform.unic.model.Page;

/**
 * 角色信息表数据库层接口
 *
 * @version v0.1, 2021-08-23 23:21:51, create by Mr.Huang.
 */
public interface ISysRoleMapper {

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    SysRoleDO selectById(Long id);

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysRole
     *            查询条件
     * @return 对象列表
     */
    List<SysRoleDO> selectList(@Param("qc") SysRoleDO sysRole);

    /**
     * 条件查询总条数
     *
     * @param sysRole
     *            查询条件
     * @return 总条数
     */
    Long selectCount(@Param("qc") SysRoleDO sysRole);

    /**
     * 查询指定行数据
     *
     * @param sysRole
     *            查询条件
     * @param page
     *            分页条件
     * @return 对象列表
     */
    List<SysRoleDO> selectPages(@Param("qc") SysRoleDO sysRole, @Param("pc") Page page);

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysRole
     *            查询条件
     * @return 结果集
     */
    List<SysRoleDO> selectByIdSet(@Param("idSet") Set<Long> idSet, @Param("qc") SysRoleDO sysRole);

    /**
     * 新增
     *
     * @param sysRole
     *            实例对象
     * @return 影响行数
     */
    int insert(@Param("qc") SysRoleDO sysRole);

    /**
     * 通过主键修改
     *
     * @param sysRole
     *            实例对象
     * @return 影响行数
     */
    int updateById(@Param("qc") SysRoleDO sysRole);

    /**
     * 通过主键删除数据
     *
     * @param id
     *            主键
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

}

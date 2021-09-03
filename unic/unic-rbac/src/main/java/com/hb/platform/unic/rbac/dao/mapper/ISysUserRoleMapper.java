package com.hb.platform.unic.rbac.dao.mapper;

import java.util.List;
import java.util.Set;

import com.hb.platform.unic.rbac.dao.dobj.SysUserRoleDO;
import org.apache.ibatis.annotations.Param;
import com.hb.platform.unic.base.model.Page;

/**
 * 用户角色关系表数据库层接口
 *
 * @version v0.1, 2021-08-23 23:22:03, create by Mr.Huang.
 */
public interface ISysUserRoleMapper {

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    SysUserRoleDO selectById(Long id);

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysUserRole
     *            查询条件
     * @return 对象列表
     */
    List<SysUserRoleDO> selectList(@Param("qc") SysUserRoleDO sysUserRole);

    /**
     * 条件查询总条数
     *
     * @param sysUserRole
     *            查询条件
     * @return 总条数
     */
    Long selectCount(@Param("qc") SysUserRoleDO sysUserRole);

    /**
     * 查询指定行数据
     *
     * @param sysUserRole
     *            查询条件
     * @param page
     *            分页条件
     * @return 对象列表
     */
    List<SysUserRoleDO> selectPages(@Param("qc") SysUserRoleDO sysUserRole, @Param("pc") Page page);

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysUserRole
     *            查询条件
     * @return 结果集
     */
    List<SysUserRoleDO> selectByIdSet(@Param("idSet") Set<Long> idSet, @Param("qc") SysUserRoleDO sysUserRole);

    /**
     * 新增
     *
     * @param sysUserRole
     *            实例对象
     * @return 影响行数
     */
    int insert(@Param("qc") SysUserRoleDO sysUserRole);

    /**
     * 通过主键修改
     *
     * @param sysUserRole
     *            实例对象
     * @return 影响行数
     */
    int updateById(@Param("qc") SysUserRoleDO sysUserRole);

    /**
     * 通过主键删除数据
     *
     * @param id
     *            主键
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

}

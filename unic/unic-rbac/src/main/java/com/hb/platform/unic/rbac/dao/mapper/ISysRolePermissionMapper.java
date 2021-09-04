package com.hb.platform.unic.rbac.dao.mapper;

import java.util.List;
import java.util.Set;

import com.hb.platform.unic.rbac.dao.dobj.SysRolePermissionDO;
import org.apache.ibatis.annotations.Param;
import com.hb.platform.unic.base.model.Page;

/**
 * 角色权限关系表数据库层接口
 *
 * @version v0.1, 2021-09-04 12:48:44, create by Mr.Huang.
 */
public interface ISysRolePermissionMapper {

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    SysRolePermissionDO selectById(Long id);

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysRolePermission
     *            查询条件
     * @return 对象列表
     */
    List<SysRolePermissionDO> selectList(@Param("qc") SysRolePermissionDO sysRolePermission);

    /**
     * 条件查询总条数
     *
     * @param sysRolePermission
     *            查询条件
     * @return 总条数
     */
    Long selectCount(@Param("qc") SysRolePermissionDO sysRolePermission);

    /**
     * 查询指定行数据
     *
     * @param sysRolePermission
     *            查询条件
     * @param page
     *            分页条件
     * @return 对象列表
     */
    List<SysRolePermissionDO> selectPages(@Param("qc") SysRolePermissionDO sysRolePermission, @Param("pc") Page page);

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysRolePermission
     *            查询条件
     * @return 结果集
     */
    List<SysRolePermissionDO> selectByIdSet(@Param("idSet") Set<Long> idSet, @Param("qc") SysRolePermissionDO sysRolePermission);

    /**
     * 新增
     *
     * @param sysRolePermission
     *            实例对象
     * @return 影响行数
     */
    int insert(@Param("qc") SysRolePermissionDO sysRolePermission);

    /**
     * 通过主键修改
     *
     * @param sysRolePermission
     *            实例对象
     * @return 影响行数
     */
    int updateById(@Param("qc") SysRolePermissionDO sysRolePermission);

    /**
     * 通过主键删除数据
     *
     * @param id
     *            主键
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

}

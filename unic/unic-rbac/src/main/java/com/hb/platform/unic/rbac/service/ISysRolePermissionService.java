package com.hb.platform.unic.rbac.service;

import java.util.List;
import java.util.Set;

import com.hb.platform.unic.rbac.dao.dobj.SysRolePermissionDO;
import com.hb.platform.unic.base.model.Page;

/**
 * 角色权限关系表服务层接口
 *
 * @version v0.1, 2021-08-23 23:21:57, create by Mr.Huang.
 */
public interface ISysRolePermissionService {

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    SysRolePermissionDO selectById(Long id);

    /**
     * 通过条件查询单条数据
     *
     * @param sysRolePermission
     *            查询条件
     * @return 实例对象
     */
    SysRolePermissionDO selectOne(SysRolePermissionDO sysRolePermission);

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysRolePermission
     *            查询条件
     * @return 对象列表
     */
    List<SysRolePermissionDO> selectList(SysRolePermissionDO sysRolePermission);

    /**
     * 分页查询数据
     *
     * @param sysRolePermission
     *            查询条件
     * @param pageNum
     *            当前页数
     * @param pageSize
     *            每页查询条数
     * @return 对象列表
     */
    Page<SysRolePermissionDO> selectPages(SysRolePermissionDO sysRolePermission, int pageNum, int pageSize);

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysRolePermission
     *            查询条件
     * @return 结果集
     */
    List<SysRolePermissionDO> selectByIdSet(Set<Long> idSet, SysRolePermissionDO sysRolePermission);


    /**
     * 新增
     *
     * @param sysRolePermission
     *            实例对象
     * @return 影响行数
     */
    int insert(SysRolePermissionDO sysRolePermission);

    /**
     * 通过主键修改
     *
     * @param sysRolePermission
     *            实例对象
     * @return 影响行数
     */
    int updateById(SysRolePermissionDO sysRolePermission);

    /**
     * 通过主键删除数据
     *
     * @param id
     *            主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
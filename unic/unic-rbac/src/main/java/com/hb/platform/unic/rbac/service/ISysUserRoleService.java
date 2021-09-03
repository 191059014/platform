package com.hb.platform.unic.rbac.service;

import java.util.List;
import java.util.Set;

import com.hb.platform.unic.rbac.dao.dobj.SysUserRoleDO;
import com.hb.platform.unic.base.model.Page;

/**
 * 用户角色关系表服务层接口
 *
 * @version v0.1, 2021-08-23 23:22:03, create by Mr.Huang.
 */
public interface ISysUserRoleService {

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    SysUserRoleDO selectById(Long id);

    /**
     * 通过条件查询单条数据
     *
     * @param sysUserRole
     *            查询条件
     * @return 实例对象
     */
    SysUserRoleDO selectOne(SysUserRoleDO sysUserRole);

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysUserRole
     *            查询条件
     * @return 对象列表
     */
    List<SysUserRoleDO> selectList(SysUserRoleDO sysUserRole);

    /**
     * 分页查询数据
     *
     * @param sysUserRole
     *            查询条件
     * @param pageNum
     *            当前页数
     * @param pageSize
     *            每页查询条数
     * @return 对象列表
     */
    Page<SysUserRoleDO> selectPages(SysUserRoleDO sysUserRole, int pageNum, int pageSize);

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysUserRole
     *            查询条件
     * @return 结果集
     */
    List<SysUserRoleDO> selectByIdSet(Set<Long> idSet, SysUserRoleDO sysUserRole);


    /**
     * 新增
     *
     * @param sysUserRole
     *            实例对象
     * @return 影响行数
     */
    int insert(SysUserRoleDO sysUserRole);

    /**
     * 通过主键修改
     *
     * @param sysUserRole
     *            实例对象
     * @return 影响行数
     */
    int updateById(SysUserRoleDO sysUserRole);

    /**
     * 通过主键删除数据
     *
     * @param id
     *            主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

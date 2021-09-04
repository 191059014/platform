package com.hb.platform.unic.rbac.service;

import java.util.List;
import java.util.Set;

import com.hb.platform.unic.base.model.Page;
import com.hb.platform.unic.rbac.dao.dobj.SysRoleDO;

/**
 * 角色信息表服务层接口
 *
 * @version v0.1, 2021-09-04 12:48:42, create by Mr.Huang.
 */
public interface ISysRoleService {

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    SysRoleDO selectById(Long id);

    /**
     * 通过条件查询单条数据
     *
     * @param sysRole
     *            查询条件
     * @return 实例对象
     */
    SysRoleDO selectOne(SysRoleDO sysRole);

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysRole
     *            查询条件
     * @return 对象列表
     */
    List<SysRoleDO> selectList(SysRoleDO sysRole);

    /**
     * 分页查询数据
     *
     * @param sysRole
     *            查询条件
     * @param pageNum
     *            当前页数
     * @param pageSize
     *            每页查询条数
     * @return 对象列表
     */
    Page<SysRoleDO> selectPages(SysRoleDO sysRole, int pageNum, int pageSize);

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysRole
     *            查询条件
     * @return 结果集
     */
    List<SysRoleDO> selectByIdSet(Set<Long> idSet, SysRoleDO sysRole);


    /**
     * 新增
     *
     * @param sysRole
     *            实例对象
     * @return 影响行数
     */
    int insert(SysRoleDO sysRole);

    /**
     * 通过主键修改
     *
     * @param sysRole
     *            实例对象
     * @return 影响行数
     */
    int updateById(SysRoleDO sysRole);

    /**
     * 通过主键删除数据
     *
     * @param id
     *            主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

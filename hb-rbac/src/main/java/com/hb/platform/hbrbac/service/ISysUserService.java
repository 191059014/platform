package com.hb.platform.hbrbac.service;

import java.util.List;
import java.util.Set;

import com.hb.platform.hbbase.model.Page;
import com.hb.platform.hbrbac.dobj.SysUserDO;

/**
 * 用户信息表服务层接口
 *
 * @version v0.1, 2021-09-04 12:48:45, create by Mr.Huang.
 */
public interface ISysUserService {

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    SysUserDO selectById(Long id);

    /**
     * 通过条件查询单条数据
     *
     * @param sysUser
     *            查询条件
     * @return 实例对象
     */
    SysUserDO selectOne(SysUserDO sysUser);

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysUser
     *            查询条件
     * @return 对象列表
     */
    List<SysUserDO> selectList(SysUserDO sysUser);

    /**
     * 分页查询数据
     *
     * @param sysUser
     *            查询条件
     * @param pageNum
     *            当前页数
     * @param pageSize
     *            每页查询条数
     * @return 对象列表
     */
    Page<SysUserDO> selectPages(SysUserDO sysUser, int pageNum, int pageSize);

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysUser
     *            查询条件
     * @return 结果集
     */
    List<SysUserDO> selectByIdSet(Set<Long> idSet, SysUserDO sysUser);


    /**
     * 新增
     *
     * @param sysUser
     *            实例对象
     * @return 影响行数
     */
    int insert(SysUserDO sysUser);

    /**
     * 通过主键修改
     *
     * @param sysUser
     *            实例对象
     * @return 影响行数
     */
    int updateById(SysUserDO sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id
     *            主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

package com.hb.platform.unic.rbac.service.impl;

import com.hb.platform.unic.rbac.dao.dobj.SysUserRoleDO;
import com.hb.platform.unic.rbac.dao.mapper.ISysUserRoleMapper;
import com.hb.platform.unic.rbac.service.ISysUserRoleService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import com.hb.platform.unic.base.model.Page;

/**
 * 用户角色关系表服务层实现类
 *
 * @version v0.1, 2021-09-04 12:48:47, create by Mr.Huang.
 */
@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    /**
     * 用户角色关系表操作数据库层
     */
    @Resource
    private ISysUserRoleMapper sysUserRoleMapper;

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    @Override
    public SysUserRoleDO selectById(Long id) {
        return this.sysUserRoleMapper.selectById(id);
    }

    /**
     * 通过条件查询单条数据
     *
     * @param sysUserRole
     *            查询条件
     * @return 对象列表
     */
    @Override
    public SysUserRoleDO selectOne(SysUserRoleDO sysUserRole) {
        List<SysUserRoleDO> list = this.sysUserRoleMapper.selectList(sysUserRole);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysUserRole
     *            查询条件
     * @return 对象列表
     */
    @Override
    public List<SysUserRoleDO> selectList(SysUserRoleDO sysUserRole) {
        return this.sysUserRoleMapper.selectList(sysUserRole);
    }

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
    @Override
    public Page<SysUserRoleDO> selectPages(SysUserRoleDO sysUserRole, int pageNum, int pageSize) {
        Long count = this.sysUserRoleMapper.selectCount(sysUserRole);
        List<SysUserRoleDO> dataList = this.sysUserRoleMapper.selectPages(sysUserRole, Page.createBefore(pageNum, pageSize));
        return Page.createAfter(count, dataList);
    }

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysUserRole
     *            查询条件
     * @return 结果集
     */
    @Override
    public List<SysUserRoleDO> selectByIdSet(Set<Long> idSet, SysUserRoleDO sysUserRole) {
        return this.sysUserRoleMapper.selectByIdSet(idSet, sysUserRole);
    }


    /**
     * 选择性新增
     *
     * @param sysUserRole
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int insert(SysUserRoleDO sysUserRole) {
        return this.sysUserRoleMapper.insert(sysUserRole);
    }

    /**
     * 通过主键修改
     *
     * @param sysUserRole
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int updateById(SysUserRoleDO sysUserRole) {
        return this.sysUserRoleMapper.updateById(sysUserRole);
    }

    /**
     * 通过主键删除数据
     *
     * @param id
     *            主键
     * @return 影响行数
     */
    @Override
    public int deleteById(Long id) {
        return this.sysUserRoleMapper.deleteById(id);
    }

}

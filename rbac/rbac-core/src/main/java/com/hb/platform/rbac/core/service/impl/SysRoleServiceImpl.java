package com.hb.platform.rbac.core.service.impl;

import com.hb.platform.rbac.core.dao.dobj.SysRoleDO;
import com.hb.platform.rbac.core.dao.mapper.ISysRoleMapper;
import com.hb.platform.rbac.core.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import com.hb.platform.unic.model.Page;

/**
 * 角色信息表服务层实现类
 *
 * @version v0.1, 2021-08-23 23:21:51, create by Mr.Huang.
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    /**
     * 角色信息表操作数据库层
     */
    @Resource
    private ISysRoleMapper sysRoleMapper;

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    @Override
    public SysRoleDO selectById(Long id) {
        return this.sysRoleMapper.selectById(id);
    }

    /**
     * 通过条件查询单条数据
     *
     * @param sysRole
     *            查询条件
     * @return 对象列表
     */
    @Override
    public SysRoleDO selectOne(SysRoleDO sysRole) {
        List<SysRoleDO> list = this.sysRoleMapper.selectList(sysRole);
        return CollectionUtils.isEmpty(list) ? new SysRoleDO() : list.get(0);
    }

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysRole
     *            查询条件
     * @return 对象列表
     */
    @Override
    public List<SysRoleDO> selectList(SysRoleDO sysRole) {
        return this.sysRoleMapper.selectList(sysRole);
    }

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
    @Override
    public Page<SysRoleDO> selectPages(SysRoleDO sysRole, int pageNum, int pageSize) {
        Long count = this.sysRoleMapper.selectCount(sysRole);
        List<SysRoleDO> dataList = this.sysRoleMapper.selectPages(sysRole, Page.createBefore(pageNum, pageSize));
        return Page.createAfter(count, dataList);
    }

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysRole
     *            查询条件
     * @return 结果集
     */
    @Override
    public List<SysRoleDO> selectByIdSet(Set<Long> idSet, SysRoleDO sysRole) {
        return this.sysRoleMapper.selectByIdSet(idSet, sysRole);
    }


    /**
     * 选择性新增
     *
     * @param sysRole
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int insert(SysRoleDO sysRole) {
        return this.sysRoleMapper.insert(sysRole);
    }

    /**
     * 通过主键修改
     *
     * @param sysRole
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int updateById(SysRoleDO sysRole) {
        return this.sysRoleMapper.updateById(sysRole);
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
        return this.sysRoleMapper.deleteById(id);
    }

}

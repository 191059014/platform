package com.hb.platform.hbrbac.service.impl;

import com.hb.platform.hbbase.model.Page;
import com.hb.platform.hbbase.model.PageCondition;
import com.hb.platform.hbrbac.mapper.ISysPermissionMapper;
import com.hb.platform.hbrbac.mapper.ISysRoleMapper;
import com.hb.platform.hbrbac.mapper.ISysRolePermissionMapper;
import com.hb.platform.hbrbac.model.dobj.SysPermissionDO;
import com.hb.platform.hbrbac.model.dobj.SysRoleDO;
import com.hb.platform.hbrbac.model.dobj.SysRolePermissionDO;
import com.hb.platform.hbrbac.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色信息表服务层实现类
 *
 * @version v0.1, 2021-09-04 12:48:42, create by Mr.Huang.
 */
@Service
@Slf4j
public class SysRoleServiceImpl implements ISysRoleService {

    /**
     * 角色信息表操作数据库层
     */
    @Resource
    private ISysRoleMapper sysRoleMapper;

    /**
     * 角色权限关系信息表操作数据库层
     */
    @Resource
    private ISysRolePermissionMapper sysRolePermissionMapper;

    /**
     * 权限信息表操作数据库层
     */
    @Resource
    private ISysPermissionMapper sysPermissionMapper;

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
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
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
        List<SysRoleDO> dataList = this.sysRoleMapper.selectPages(sysRole, PageCondition.create(pageNum, pageSize));
        return Page.create(count, dataList);
    }

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @return 结果集
     */
    @Override
    public List<SysRoleDO> selectByIdSet(Set<Long> idSet) {
        return this.sysRoleMapper.selectByIdSet(idSet);
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
     * @param sysRole
     *            主键
     * @return 影响行数
     */
    @Override
    public int deleteById(SysRoleDO sysRole) {
        return this.sysRoleMapper.deleteById(sysRole);
    }

    @Override
    public Set<Long> getPermissionIdSetUnderTenantRole() {
        Set<Long> permissionIdSet = null;
        List<SysRoleDO> roleList = sysRoleMapper.selectList(new SysRoleDO());
        if (!CollectionUtils.isEmpty(roleList)) {
            Set<Long> roleIdSet = roleList.stream().map(SysRoleDO::getId).collect(Collectors.toSet());
            List<SysRolePermissionDO> rolePermissionList = sysRolePermissionMapper.selectByRoleIdSet(roleIdSet);
            if (!CollectionUtils.isEmpty(rolePermissionList)) {
                permissionIdSet =
                    rolePermissionList.stream().map(SysRolePermissionDO::getPermissionId).collect(Collectors.toSet());
            }
        }
        return permissionIdSet;
    }

    @Override
    public List<SysPermissionDO> getPermissionListUnderTenantRole() {
        List<SysPermissionDO> list = null;
        Set<Long> permissionIdSet = getPermissionIdSetUnderTenantRole();
        if (!CollectionUtils.isEmpty(permissionIdSet)) {
            list = sysPermissionMapper.selectByIdSet(permissionIdSet, new SysPermissionDO());
        }
        return list;
    }

}

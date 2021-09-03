package com.hb.platform.unic.rbac.service.impl;

import com.hb.platform.unic.rbac.dao.dobj.SysRolePermissionDO;
import com.hb.platform.unic.rbac.dao.mapper.ISysRolePermissionMapper;
import com.hb.platform.unic.rbac.service.ISysRolePermissionService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import com.hb.platform.unic.base.model.Page;

/**
 * 角色权限关系表服务层实现类
 *
 * @version v0.1, 2021-08-23 23:21:57, create by Mr.Huang.
 */
@Service
public class SysRolePermissionServiceImpl implements ISysRolePermissionService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysRolePermissionServiceImpl.class);

    /**
     * 角色权限关系表操作数据库层
     */
    @Resource
    private ISysRolePermissionMapper sysRolePermissionMapper;

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    @Override
    public SysRolePermissionDO selectById(Long id) {
        return this.sysRolePermissionMapper.selectById(id);
    }

    /**
     * 通过条件查询单条数据
     *
     * @param sysRolePermission
     *            查询条件
     * @return 对象列表
     */
    @Override
    public SysRolePermissionDO selectOne(SysRolePermissionDO sysRolePermission) {
        List<SysRolePermissionDO> list = this.sysRolePermissionMapper.selectList(sysRolePermission);
        return CollectionUtils.isEmpty(list) ? new SysRolePermissionDO() : list.get(0);
    }

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysRolePermission
     *            查询条件
     * @return 对象列表
     */
    @Override
    public List<SysRolePermissionDO> selectList(SysRolePermissionDO sysRolePermission) {
        return this.sysRolePermissionMapper.selectList(sysRolePermission);
    }

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
    @Override
    public Page<SysRolePermissionDO> selectPages(SysRolePermissionDO sysRolePermission, int pageNum, int pageSize) {
        Long count = this.sysRolePermissionMapper.selectCount(sysRolePermission);
        List<SysRolePermissionDO> dataList = this.sysRolePermissionMapper.selectPages(sysRolePermission, Page.createBefore(pageNum, pageSize));
        return Page.createAfter(count, dataList);
    }

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysRolePermission
     *            查询条件
     * @return 结果集
     */
    @Override
    public List<SysRolePermissionDO> selectByIdSet(Set<Long> idSet, SysRolePermissionDO sysRolePermission) {
        return this.sysRolePermissionMapper.selectByIdSet(idSet, sysRolePermission);
    }


    /**
     * 选择性新增
     *
     * @param sysRolePermission
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int insert(SysRolePermissionDO sysRolePermission) {
        return this.sysRolePermissionMapper.insert(sysRolePermission);
    }

    /**
     * 通过主键修改
     *
     * @param sysRolePermission
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int updateById(SysRolePermissionDO sysRolePermission) {
        return this.sysRolePermissionMapper.updateById(sysRolePermission);
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
        return this.sysRolePermissionMapper.deleteById(id);
    }

}

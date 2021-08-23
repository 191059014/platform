package com.hb.platform.rbac.core.service.impl;

import com.hb.platform.rbac.core.dao.dobj.SysPermissionDO;
import com.hb.platform.rbac.core.dao.mapper.ISysPermissionMapper;
import com.hb.platform.rbac.core.service.ISysPermissionService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import com.hb.platform.unic.model.Page;

/**
 * 权限信息表服务层实现类
 *
 * @version v0.1, 2021-08-23 23:06:14, create by Mr.Huang.
 */
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysPermissionServiceImpl.class);

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
    public SysPermissionDO selectById(Long id) {
        return this.sysPermissionMapper.selectById(id);
    }

    /**
     * 通过条件查询单条数据
     *
     * @param sysPermission
     *            查询条件
     * @return 对象列表
     */
    @Override
    public SysPermissionDO selectOne(SysPermissionDO sysPermission) {
        List<SysPermissionDO> list = this.sysPermissionMapper.selectList(sysPermission);
        return CollectionUtils.isEmpty(list) ? new SysPermissionDO() : list.get(0);
    }

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysPermission
     *            查询条件
     * @return 对象列表
     */
    @Override
    public List<SysPermissionDO> selectList(SysPermissionDO sysPermission) {
        return this.sysPermissionMapper.selectList(sysPermission);
    }

    /**
     * 分页查询数据
     *
     * @param sysPermission
     *            查询条件
     * @param pageNum
     *            当前页数
     * @param pageSize
     *            每页查询条数
     * @return 对象列表
     */
    @Override
    public Page<SysPermissionDO> selectPages(SysPermissionDO sysPermission, int pageNum, int pageSize) {
        Long count = this.sysPermissionMapper.selectCount(sysPermission);
        List<SysPermissionDO> dataList = this.sysPermissionMapper.selectPages(sysPermission, Page.createBefore(pageNum, pageSize));
        return Page.createAfter(count, dataList);
    }

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysPermission
     *            查询条件
     * @return 结果集
     */
    @Override
    public List<SysPermissionDO> selectByIdSet(Set<Long> idSet, SysPermissionDO sysPermission) {
        return this.sysPermissionMapper.selectByIdSet(idSet, sysPermission);
    }


    /**
     * 选择性新增
     *
     * @param sysPermission
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int insert(SysPermissionDO sysPermission) {
        return this.sysPermissionMapper.insert(sysPermission);
    }

    /**
     * 通过主键修改
     *
     * @param sysPermission
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int updateById(SysPermissionDO sysPermission) {
        return this.sysPermissionMapper.updateById(sysPermission);
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
        return this.sysPermissionMapper.deleteById(id);
    }

}

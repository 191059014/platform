package com.hb.platform.hbrbac.service.impl;

import com.hb.platform.hbbase.model.Page;
import com.hb.platform.hbbase.model.PageCondition;
import com.hb.platform.hbrbac.dobj.SysUserDO;
import com.hb.platform.hbrbac.mapper.ISysUserMapper;
import com.hb.platform.hbrbac.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 用户信息表服务层实现类
 *
 * @version v0.1, 2021-09-04 12:48:45, create by Mr.Huang.
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);

    /**
     * 用户信息表操作数据库层
     */
    @Resource
    private ISysUserMapper sysUserMapper;

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    @Override
    public SysUserDO selectById(Long id) {
        return this.sysUserMapper.selectById(id);
    }

    /**
     * 通过条件查询单条数据
     *
     * @param sysUser
     *            查询条件
     * @return 对象列表
     */
    @Override
    public SysUserDO selectOne(SysUserDO sysUser) {
        List<SysUserDO> list = this.sysUserMapper.selectList(sysUser);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysUser
     *            查询条件
     * @return 对象列表
     */
    @Override
    public List<SysUserDO> selectList(SysUserDO sysUser) {
        return this.sysUserMapper.selectList(sysUser);
    }

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
    @Override
    public Page<SysUserDO> selectPages(SysUserDO sysUser, int pageNum, int pageSize) {
        Long count = this.sysUserMapper.selectCount(sysUser);
        List<SysUserDO> dataList = this.sysUserMapper.selectPages(sysUser, PageCondition.create(pageNum, pageSize));
        return Page.create(count, dataList);
    }

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysUser
     *            查询条件
     * @return 结果集
     */
    @Override
    public List<SysUserDO> selectByIdSet(Set<Long> idSet, SysUserDO sysUser) {
        return this.sysUserMapper.selectByIdSet(idSet, sysUser);
    }

    /**
     * 选择性新增
     *
     * @param sysUser
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int insert(SysUserDO sysUser) {
        return this.sysUserMapper.insert(sysUser);
    }

    /**
     * 通过主键修改
     *
     * @param sysUser
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int updateById(SysUserDO sysUser) {
        return this.sysUserMapper.updateById(sysUser);
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
        return this.sysUserMapper.deleteById(id);
    }

}

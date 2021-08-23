package com.hb.platform.configcenter.core.service.impl;

import com.hb.platform.configcenter.core.dao.dobj.CfgGlobalDO;
import com.hb.platform.configcenter.core.dao.mapper.ICfgGlobalMapper;
import com.hb.platform.configcenter.core.service.ICfgGlobalService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import com.hb.platform.unic.model.Page;

/**
 * 全局配置表服务层实现类
 *
 * @version v0.1, 2021-08-23 22:44:04, create by Mr.Huang.
 */
@Service
public class CfgGlobalServiceImpl implements ICfgGlobalService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CfgGlobalServiceImpl.class);

    /**
     * 全局配置表操作数据库层
     */
    @Resource
    private ICfgGlobalMapper cfgGlobalMapper;

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    @Override
    public CfgGlobalDO selectById(Long id) {
        return this.cfgGlobalMapper.selectById(id);
    }

    /**
     * 通过条件查询单条数据
     *
     * @param cfgGlobal
     *            查询条件
     * @return 对象列表
     */
    @Override
    public CfgGlobalDO selectOne(CfgGlobalDO cfgGlobal) {
        List<CfgGlobalDO> list = this.cfgGlobalMapper.selectList(cfgGlobal);
        return CollectionUtils.isEmpty(list) ? new CfgGlobalDO() : list.get(0);
    }

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param cfgGlobal
     *            查询条件
     * @return 对象列表
     */
    @Override
    public List<CfgGlobalDO> selectList(CfgGlobalDO cfgGlobal) {
        return this.cfgGlobalMapper.selectList(cfgGlobal);
    }

    /**
     * 分页查询数据
     *
     * @param cfgGlobal
     *            查询条件
     * @param pageNum
     *            当前页数
     * @param pageSize
     *            每页查询条数
     * @return 对象列表
     */
    @Override
    public Page<CfgGlobalDO> selectPages(CfgGlobalDO cfgGlobal, int pageNum, int pageSize) {
        Long count = this.cfgGlobalMapper.selectCount(cfgGlobal);
        List<CfgGlobalDO> dataList = this.cfgGlobalMapper.selectPages(cfgGlobal, Page.createBefore(pageNum, pageSize));
        return Page.createAfter(count, dataList);
    }

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param cfgGlobal
     *            查询条件
     * @return 结果集
     */
    @Override
    public List<CfgGlobalDO> selectByIdSet(Set<Long> idSet, CfgGlobalDO cfgGlobal) {
        return this.cfgGlobalMapper.selectByIdSet(idSet, cfgGlobal);
    }


    /**
     * 选择性新增
     *
     * @param cfgGlobal
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int insert(CfgGlobalDO cfgGlobal) {
        return this.cfgGlobalMapper.insert(cfgGlobal);
    }

    /**
     * 通过主键修改
     *
     * @param cfgGlobal
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int updateById(CfgGlobalDO cfgGlobal) {
        return this.cfgGlobalMapper.updateById(cfgGlobal);
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
        return this.cfgGlobalMapper.deleteById(id);
    }

}

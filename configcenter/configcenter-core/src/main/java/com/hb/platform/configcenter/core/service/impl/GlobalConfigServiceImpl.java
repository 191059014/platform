package com.hb.platform.configcenter.core.service.impl;

import com.hb.platform.configcenter.core.dao.dobj.GlobalConfigDO;
import com.hb.platform.configcenter.core.dao.mapper.IGlobalConfigMapper;
import com.hb.platform.configcenter.core.service.IGlobalConfigService;
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
 * @version v0.1, 2021-08-22 20:39:39, create by Mr.Huang.
 */
@Service
public class GlobalConfigServiceImpl implements IGlobalConfigService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalConfigServiceImpl.class);

    /**
     * 全局配置表操作数据库层
     */
    @Resource
    private IGlobalConfigMapper globalConfigMapper;

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    @Override
    public GlobalConfigDO selectById(Long id) {
        return this.globalConfigMapper.selectById(id);
    }

    /**
     * 通过条件查询单条数据
     *
     * @param globalConfig
     *            查询条件
     * @return 对象列表
     */
    @Override
    public GlobalConfigDO selectOne(GlobalConfigDO globalConfig) {
        List<GlobalConfigDO> list = this.globalConfigMapper.selectList(globalConfig);
        return CollectionUtils.isEmpty(list) ? new GlobalConfigDO() : list.get(0);
    }

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param globalConfig
     *            查询条件
     * @return 对象列表
     */
    @Override
    public List<GlobalConfigDO> selectList(GlobalConfigDO globalConfig) {
        return this.globalConfigMapper.selectList(globalConfig);
    }

    /**
     * 分页查询数据
     *
     * @param globalConfig
     *            查询条件
     * @param pageNum
     *            当前页数
     * @param pageSize
     *            每页查询条数
     * @return 对象列表
     */
    @Override
    public Page<GlobalConfigDO> selectPages(GlobalConfigDO globalConfig, int pageNum, int pageSize) {
        Long count = this.globalConfigMapper.selectCount(globalConfig);
        List<GlobalConfigDO> dataList = this.globalConfigMapper.selectPages(globalConfig, Page.createBefore(pageNum, pageSize));
        return Page.createAfter(count, dataList);
    }

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param globalConfig
     *            查询条件
     * @return 结果集
     */
    @Override
    public List<GlobalConfigDO> selectByIdSet(Set<Long> idSet, GlobalConfigDO globalConfig) {
        return this.globalConfigMapper.selectByIdSet(idSet, globalConfig);
    }


    /**
     * 选择性新增
     *
     * @param globalConfig
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int insert(GlobalConfigDO globalConfig) {
        return this.globalConfigMapper.insert(globalConfig);
    }

    /**
     * 通过主键修改
     *
     * @param globalConfig
     *            实例对象
     * @return 影响行数
     */
    @Override
    public int updateById(GlobalConfigDO globalConfig) {
        return this.globalConfigMapper.updateById(globalConfig);
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
        return this.globalConfigMapper.deleteById(id);
    }

}

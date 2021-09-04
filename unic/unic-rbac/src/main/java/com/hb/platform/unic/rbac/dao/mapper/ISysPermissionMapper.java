package com.hb.platform.unic.rbac.dao.mapper;

import java.util.List;
import java.util.Set;

import com.hb.platform.unic.rbac.dao.dobj.SysPermissionDO;
import org.apache.ibatis.annotations.Param;
import com.hb.platform.unic.base.model.Page;

/**
 * 权限信息表数据库层接口
 *
 * @version v0.1, 2021-09-04 12:48:40, create by Mr.Huang.
 */
public interface ISysPermissionMapper {

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 实例对象
     */
    SysPermissionDO selectById(Long id);

    /**
     * 通过实体作为筛选条件查询集合
     *
     * @param sysPermission
     *            查询条件
     * @return 对象列表
     */
    List<SysPermissionDO> selectList(@Param("qc") SysPermissionDO sysPermission);

    /**
     * 条件查询总条数
     *
     * @param sysPermission
     *            查询条件
     * @return 总条数
     */
    Long selectCount(@Param("qc") SysPermissionDO sysPermission);

    /**
     * 查询指定行数据
     *
     * @param sysPermission
     *            查询条件
     * @param page
     *            分页条件
     * @return 对象列表
     */
    List<SysPermissionDO> selectPages(@Param("qc") SysPermissionDO sysPermission, @Param("pc") Page page);

    /**
     * 通过id集合查询
     *
     * @param idSet
     *            id集合
     * @param sysPermission
     *            查询条件
     * @return 结果集
     */
    List<SysPermissionDO> selectByIdSet(@Param("idSet") Set<Long> idSet, @Param("qc") SysPermissionDO sysPermission);

    /**
     * 新增
     *
     * @param sysPermission
     *            实例对象
     * @return 影响行数
     */
    int insert(@Param("qc") SysPermissionDO sysPermission);

    /**
     * 通过主键修改
     *
     * @param sysPermission
     *            实例对象
     * @return 影响行数
     */
    int updateById(@Param("qc") SysPermissionDO sysPermission);

    /**
     * 通过主键删除数据
     *
     * @param id
     *            主键
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

}

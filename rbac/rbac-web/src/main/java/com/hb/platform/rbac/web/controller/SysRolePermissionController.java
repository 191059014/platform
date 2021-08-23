package com.hb.platform.rbac.web.controller;

import com.hb.platform.rbac.core.service.ISysRolePermissionService;
import com.hb.platform.rbac.core.dao.dobj.SysRolePermissionDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Resource;

import com.hb.platform.unic.model.Page;
import com.hb.platform.unic.model.Result;

/**
 * 角色权限关系表控制层
 *
 * @version v0.1, 2021-08-23 23:21:57, create by Mr.Huang.
 */
@RestController
@RequestMapping("/sysRolePermission")
public class SysRolePermissionController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysRolePermissionController.class);

    /**
     * 角色权限关系表服务层
     */
    @Resource
    private ISysRolePermissionService sysRolePermissionService;

    /**
     * 分页查询角色权限关系表
     *
     * @param sysRolePermission
     *              查询条件对象
     * @param pageNum
     *              当前第几页
     * @param pageSize
     *              每页条数
     * @return 分页结果
     */
    @PostMapping("/queryPages")
    public Result<Page<SysRolePermissionDO>> queryPages(@RequestBody SysRolePermissionDO sysRolePermission,
                                                        @RequestParam("pageNum") Integer pageNum,
                                                        @RequestParam("pageSize") Integer pageSize) {
        return Result.success(sysRolePermissionService.selectPages(sysRolePermission, pageNum, pageSize));
    }

    /**
     * 新增角色权限关系表
     *
     * @param sysRolePermission
     *            新增对象信息
     * @return 影响的行数
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysRolePermissionDO sysRolePermission) {
        return Result.success(sysRolePermissionService.insert(sysRolePermission));
    }

    /**
     * 通过主键修改角色权限关系表
     *
     * @param sysRolePermission
     *            要修改的信息
     * @return 影响的行数
     */
    @PostMapping("/updateById")
    public Result updateById(@RequestBody SysRolePermissionDO sysRolePermission) {
        return Result.success(sysRolePermissionService.updateById(sysRolePermission));
    }

    /**
     * 通过主键删除角色权限关系表
     *
     * @param id
     *            主键
     * @return 影响的行数
     */
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Long id) {
        return Result.success(sysRolePermissionService.deleteById(id));
    }

}

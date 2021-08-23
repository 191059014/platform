package com.hb.platform.rbac.web.controller;

import com.hb.platform.rbac.core.service.ISysRoleService;
import com.hb.platform.rbac.core.dao.dobj.SysRoleDO;
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
 * 角色信息表控制层
 *
 * @version v0.1, 2021-08-23 23:21:51, create by Mr.Huang.
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleController.class);

    /**
     * 角色信息表服务层
     */
    @Resource
    private ISysRoleService sysRoleService;

    /**
     * 分页查询角色信息表
     *
     * @param sysRole
     *              查询条件对象
     * @param pageNum
     *              当前第几页
     * @param pageSize
     *              每页条数
     * @return 分页结果
     */
    @PostMapping("/queryPages")
    public Result<Page<SysRoleDO>> queryPages(@RequestBody SysRoleDO sysRole,
                                              @RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize) {
        return Result.success(sysRoleService.selectPages(sysRole, pageNum, pageSize));
    }

    /**
     * 新增角色信息表
     *
     * @param sysRole
     *            新增对象信息
     * @return 影响的行数
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysRoleDO sysRole) {
        return Result.success(sysRoleService.insert(sysRole));
    }

    /**
     * 通过主键修改角色信息表
     *
     * @param sysRole
     *            要修改的信息
     * @return 影响的行数
     */
    @PostMapping("/updateById")
    public Result updateById(@RequestBody SysRoleDO sysRole) {
        return Result.success(sysRoleService.updateById(sysRole));
    }

    /**
     * 通过主键删除角色信息表
     *
     * @param id
     *            主键
     * @return 影响的行数
     */
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Long id) {
        return Result.success(sysRoleService.deleteById(id));
    }

}

package com.hb.platform.unic.rbac.controller;

import com.hb.platform.unic.base.model.Page;
import com.hb.platform.unic.base.model.Result;
import com.hb.platform.unic.rbac.service.ISysPermissionService;
import com.hb.platform.unic.rbac.dao.dobj.SysPermissionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 权限信息表控制层
 *
 * @version v0.1, 2021-09-04 12:48:40, create by Mr.Huang.
 */
@RestController
@RequestMapping("/sysPermission")
public class SysPermissionController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysPermissionController.class);

    /**
     * 权限信息表服务层
     */
    @Resource
    private ISysPermissionService sysPermissionService;

    /**
     * 分页查询权限信息表
     *
     * @param sysPermission
     *            查询条件对象
     * @param pageNum
     *            当前第几页
     * @param pageSize
     *            每页条数
     * @return 分页结果
     */
    @PostMapping("/queryPages")
    public Result<Page<SysPermissionDO>> queryPages(@RequestBody SysPermissionDO sysPermission,
                                                    @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return Result.success(sysPermissionService.selectPages(sysPermission, pageNum, pageSize));
    }

    /**
     * 新增权限信息表
     *
     * @param sysPermission
     *            新增对象信息
     * @return 影响的行数
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysPermissionDO sysPermission) {
        return Result.success(sysPermissionService.insert(sysPermission));
    }

    /**
     * 通过主键修改权限信息表
     *
     * @param sysPermission
     *            要修改的信息
     * @return 影响的行数
     */
    @PostMapping("/updateById")
    public Result updateById(@RequestBody SysPermissionDO sysPermission) {
        return Result.success(sysPermissionService.updateById(sysPermission));
    }

    /**
     * 通过主键删除权限信息表
     *
     * @param id
     *            主键
     * @return 影响的行数
     */
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Long id) {
        return Result.success(sysPermissionService.deleteById(id));
    }

}

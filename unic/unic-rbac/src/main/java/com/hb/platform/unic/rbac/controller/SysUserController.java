package com.hb.platform.unic.rbac.controller;

import com.hb.platform.unic.base.model.Page;
import com.hb.platform.unic.base.model.Result;
import com.hb.platform.unic.rbac.service.ISysUserService;
import com.hb.platform.unic.rbac.dao.dobj.SysUserDO;
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
 * 用户信息表控制层
 *
 * @version v0.1, 2021-09-04 12:48:45, create by Mr.Huang.
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

    /**
     * 用户信息表服务层
     */
    @Resource
    private ISysUserService sysUserService;

    /**
     * 分页查询用户信息表
     *
     * @param sysUser
     *            查询条件对象
     * @param pageNum
     *            当前第几页
     * @param pageSize
     *            每页条数
     * @return 分页结果
     */
    @PostMapping("/queryPages")
    public Result<Page<SysUserDO>> queryPages(@RequestBody SysUserDO sysUser, @RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize) {
        return Result.success(sysUserService.selectPages(sysUser, pageNum, pageSize));
    }

    /**
     * 新增用户信息表
     *
     * @param sysUser
     *            新增对象信息
     * @return 影响的行数
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysUserDO sysUser) {
        return Result.success(sysUserService.insert(sysUser));
    }

    /**
     * 通过主键修改用户信息表
     *
     * @param sysUser
     *            要修改的信息
     * @return 影响的行数
     */
    @PostMapping("/updateById")
    public Result updateById(@RequestBody SysUserDO sysUser) {
        return Result.success(sysUserService.updateById(sysUser));
    }

    /**
     * 通过主键删除用户信息表
     *
     * @param id
     *            主键
     * @return 影响的行数
     */
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Long id) {
        return Result.success(sysUserService.deleteById(id));
    }

}

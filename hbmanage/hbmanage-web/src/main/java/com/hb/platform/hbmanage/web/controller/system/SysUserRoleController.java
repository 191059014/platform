package com.hb.platform.hbmanage.web.controller.system;

import com.hb.platform.hbmanage.web.common.Result;
import com.hb.platform.hbmanage.web.common.enums.ResultCode;
import com.hb.platform.unic.base.model.Page;
import com.hb.platform.unic.common.validator.Assert;
import com.hb.platform.unic.common.validator.Check;
import com.hb.platform.unic.rbac.dobj.SysUserRoleDO;
import com.hb.platform.unic.rbac.service.ISysUserRoleService;
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
 * 用户角色关系表控制层
 *
 * @version v0.1, 2021-09-04 12:48:47, create by Mr.Huang.
 */
@RestController
@RequestMapping("/sysUserRole")
public class SysUserRoleController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserRoleController.class);

    /**
     * 用户角色关系表服务层
     */
    @Resource
    private ISysUserRoleService sysUserRoleService;

    /**
     * 分页查询用户角色关系表
     *
     * @param sysUserRole
     *            查询条件对象
     * @param pageNum
     *            当前第几页
     * @param pageSize
     *            每页条数
     * @return 分页结果
     */
    @PostMapping("/queryPages")
    public Result<Page<SysUserRoleDO>> queryPages(@RequestBody SysUserRoleDO sysUserRole,
        @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        Assert.ifTrueThrows(Check.incorrectPageParameter(pageNum, pageSize), ResultCode.PAGE_PARAM_ERROR);
        return Result.success(sysUserRoleService.selectPages(sysUserRole, pageNum, pageSize));
    }

    /**
     * 新增用户角色关系表
     *
     * @param sysUserRole
     *            新增对象信息
     * @return 影响的行数
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysUserRoleDO sysUserRole) {
        return Result.success(sysUserRoleService.insert(sysUserRole));
    }

    /**
     * 通过主键修改用户角色关系表
     *
     * @param sysUserRole
     *            要修改的信息
     * @return 影响的行数
     */
    @PostMapping("/updateById")
    public Result updateById(@RequestBody SysUserRoleDO sysUserRole) {
        return Result.success(sysUserRoleService.updateById(sysUserRole));
    }

    /**
     * 通过主键删除用户角色关系表
     *
     * @param id
     *            主键
     * @return 影响的行数
     */
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Long id) {
        return Result.success(sysUserRoleService.deleteById(id));
    }

}

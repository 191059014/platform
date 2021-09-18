package com.hb.platform.hbrbac.controller;

import com.hb.platform.hbbase.annotation.InOutLog;
import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.common.ResultCode;
import com.hb.platform.hbbase.model.Page;
import com.hb.platform.hbcommon.constant.CommonConsts;
import com.hb.platform.hbcommon.security.SingleTrackEncrypt;
import com.hb.platform.hbcommon.validator.Assert;
import com.hb.platform.hbcommon.validator.Check;
import com.hb.platform.hbrbac.RbacContext;
import com.hb.platform.hbrbac.model.dobj.SysUserDO;
import com.hb.platform.hbrbac.model.dobj.SysUserRoleDO;
import com.hb.platform.hbrbac.service.ISysUserRoleService;
import com.hb.platform.hbrbac.service.ISysUserService;
import com.hb.platform.hbrbac.util.RbacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
     * 用户角色信息表服务层
     */
    @Resource
    private ISysUserRoleService sysUserRoleService;

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
        Assert.ifTrueThrows(Check.incorrectPageParameter(pageNum, pageSize), ResultCode.PAGE_PARAM_ERROR);
        Long currentTenantId = RbacContext.getCurrentTenantId();
        if (!RbacUtils.isSuperAdmin(currentTenantId)) {
            // 非超级管理员，只能查询当前用户对应商户下的用户
            if (sysUser.getTenantId() == null) {
                sysUser.setTenantId(currentTenantId);
            }
        }
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
    @InOutLog("新增用户")
    public Result save(@RequestBody SysUserDO sysUser) {
        Assert.hasText(sysUser.getUserName(), ResultCode.PARAM_ILLEGAL);
        Assert.hasText(sysUser.getPassword(), ResultCode.PARAM_ILLEGAL);
        String encodePassword = SingleTrackEncrypt.MD5.encode(sysUser.getPassword(), CommonConsts.UTF_8);
        sysUser.setPassword(encodePassword);
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

    /**
     * 更新用户的角色
     * 
     * @param roleIdSet
     *            角色id
     * @param userId
     *            用户id
     * @return 结果
     */
    @PostMapping("updateUserRole")
    @InOutLog("更新用户的角色")
    public Result updateUserRole(@RequestBody Set<Long> roleIdSet, @RequestParam("userId") Long userId) {
        Assert.notNull(userId, ResultCode.PARAM_ILLEGAL);
        Assert.notEmpty(roleIdSet, ResultCode.PARAM_ILLEGAL);
        // 删除用户的所有角色
        sysUserRoleService.deleteByUserId(userId);
        // 批量新增用户角色关系
        List<SysUserRoleDO> list = new ArrayList<>();
        for (Long roleId : roleIdSet) {
            SysUserRoleDO userRole = new SysUserRoleDO();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            list.add(userRole);
        }
        return Result.success(sysUserRoleService.insertBatch(list));
    }

}

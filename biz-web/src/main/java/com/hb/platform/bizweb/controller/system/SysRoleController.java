package com.hb.platform.bizweb.controller.system;

import com.hb.platform.bizweb.common.Result;
import com.hb.platform.bizweb.common.enums.ResultCode;
import com.hb.platform.bizweb.common.util.HbmanageUtils;
import com.hb.platform.bizweb.container.RbacContext;
import com.hb.platform.hbbase.annotation.InOutLog;
import com.hb.platform.hbbase.model.Page;
import com.hb.platform.hbcommon.validator.Assert;
import com.hb.platform.hbcommon.validator.Check;
import com.hb.platform.hbrbac.dobj.SysRoleDO;
import com.hb.platform.hbrbac.dobj.SysUserRoleDO;
import com.hb.platform.hbrbac.service.ISysRoleService;
import com.hb.platform.hbrbac.service.ISysUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色信息表控制层
 *
 * @version v0.1, 2021-09-04 12:48:42, create by Mr.Huang.
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
     * 用户角色关系表服务层
     */
    @Resource
    private ISysUserRoleService sysUserRoleService;

    /**
     * 分页查询角色信息表
     *
     * @param sysRole
     *            查询条件对象
     * @param pageNum
     *            当前第几页
     * @param pageSize
     *            每页条数
     * @return 分页结果
     */
    @PostMapping("/queryPages")
    public Result<Page<SysRoleDO>> queryPages(@RequestBody SysRoleDO sysRole, @RequestParam("pageNum") Integer pageNum,
        @RequestParam("pageSize") Integer pageSize) {
        Assert.ifTrueThrows(Check.incorrectPageParameter(pageNum, pageSize), ResultCode.PAGE_PARAM_ERROR);
        Long currentTenantId = RbacContext.getCurrentTenantId();
        if (!HbmanageUtils.isSuperAdmin(currentTenantId)) {
            // 非超级管理员，只能查询当前用户对应商户下的角色
            if (sysRole.getTenantId() == null) {
                sysRole.setTenantId(currentTenantId);
            }
        }
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
        Assert.hasText(sysRole.getRoleName(), ResultCode.PARAM_ILLEGAL);
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
        Assert.notNull(sysRole.getId(), ResultCode.PARAM_ILLEGAL);
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
        Assert.notNull(id, ResultCode.PARAM_ILLEGAL);
        return Result.success(sysRoleService.deleteById(id));
    }

    /**
     * 获取当前用户对应商户下的所有角色
     *
     * @return 结果
     */
    @GetMapping("/getRolesUnderMerchant")
    @InOutLog("获取当前用户对应商户下的所有角色")
    public Result<List<SysRoleDO>> getRolesUnderMerchant() {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        SysRoleDO query = new SysRoleDO();
        if (!HbmanageUtils.isSuperAdmin(currentTenantId)) {
            query.setTenantId(currentTenantId);
        }
        return Result.success(sysRoleService.selectList(query));
    }

    /**
     * 获取指定用户拥有的角色
     *
     * @return 结果
     */
    @GetMapping("/getRolesUnderUser")
    @InOutLog("获取指定用户拥有的角色")
    public Result<List<SysRoleDO>> getRolesUnderUser(@RequestParam("userId") Long userId) {
        Assert.notNull(userId, ResultCode.PARAM_ILLEGAL);
        List<SysRoleDO> roleList = null;
        SysUserRoleDO userRoleQuery = new SysUserRoleDO();
        userRoleQuery.setUserId(userId);
        List<SysUserRoleDO> userRoleList = sysUserRoleService.selectList(userRoleQuery);
        if (!CollectionUtils.isEmpty(userRoleList)) {
            Set<Long> roleIdSet = userRoleList.stream().map(SysUserRoleDO::getRoleId).collect(Collectors.toSet());
            roleList = sysRoleService.selectByIdSet(roleIdSet);
        }
        return Result.success(roleList);
    }

}

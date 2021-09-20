package com.hb.platform.hbrbac.controller;

import com.hb.platform.hbbase.annotation.InOutLog;
import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.common.ResultCode;
import com.hb.platform.hbbase.dao.dobj.base.impl.AbstractBaseDO;
import com.hb.platform.hbbase.model.Page;
import com.hb.platform.hbcommon.validator.Assert;
import com.hb.platform.hbcommon.validator.Check;
import com.hb.platform.hbrbac.RbacContext;
import com.hb.platform.hbrbac.enums.ResourceType;
import com.hb.platform.hbrbac.model.dobj.SysPermissionDO;
import com.hb.platform.hbrbac.model.dobj.SysRolePermissionDO;
import com.hb.platform.hbrbac.model.dobj.SysUserDO;
import com.hb.platform.hbrbac.model.dobj.SysUserRoleDO;
import com.hb.platform.hbrbac.model.dto.ElementuiMenu;
import com.hb.platform.hbrbac.model.vo.response.ElementuiMenuResponse;
import com.hb.platform.hbrbac.service.ISysPermissionService;
import com.hb.platform.hbrbac.service.ISysRolePermissionService;
import com.hb.platform.hbrbac.service.ISysUserRoleService;
import com.hb.platform.hbrbac.service.ISysUserService;
import com.hb.platform.hbrbac.util.RbacUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 用户信息表控制层
 *
 * @version v0.1, 2021-09-04 12:48:45, create by Mr.Huang.
 */
@RestController
@RequestMapping("/sysUser")
@Slf4j
public class SysUserController {

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
     * 权限信息表服务层
     */
    @Resource
    private ISysPermissionService sysPermissionService;

    /**
     * 角色权限关系表服务层
     */
    @Resource
    private ISysRolePermissionService sysRolePermissionService;

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
    @PreAuthorize("hasAuthority('user_manage')")
    @PostMapping("/queryPages")
    public Result<Page<SysUserDO>> queryPages(@RequestBody SysUserDO sysUser, @RequestParam("pageNum") Integer pageNum,
        @RequestParam("pageSize") Integer pageSize) {
        Assert.ifTrueThrows(Check.incorrectPageParameter(pageNum, pageSize), ResultCode.PAGE_PARAM_ERROR);
        return Result.success(sysUserService.selectPages(sysUser, pageNum, pageSize));
    }

    /**
     * 新增用户信息表
     *
     * @param sysUser
     *            新增对象信息
     * @return 影响的行数
     */
    @PreAuthorize("hasAuthority('user_manage_add')")
    @PostMapping("/save")
    @InOutLog("新增用户")
    public Result save(@RequestBody SysUserDO sysUser) {
        Assert.hasText(sysUser.getUserName(), ResultCode.PARAM_ILLEGAL);
        Assert.hasText(sysUser.getPassword(), ResultCode.PARAM_ILLEGAL);
        String encodePassword = new BCryptPasswordEncoder().encode(sysUser.getPassword());
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
    @PreAuthorize("hasAuthority('user_manage_update')")
    @PostMapping("/updateById")
    public Result updateById(@RequestBody SysUserDO sysUser) {
        if (StringUtils.isNotBlank(sysUser.getPassword())) {
            String encodePassword = new BCryptPasswordEncoder().encode(sysUser.getPassword());
            sysUser.setPassword(encodePassword);
        }
        return Result.success(sysUserService.updateById(sysUser));
    }

    /**
     * 通过主键删除用户信息表
     *
     * @param id
     *            主键
     * @return 影响的行数
     */
    @PreAuthorize("hasAuthority('user_manage_delete')")
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Long id) {
        Assert.notNull(id, ResultCode.PARAM_ILLEGAL);
        SysUserDO sysUser = new SysUserDO();
        sysUser.setId(id);
        return Result.success(sysUserService.deleteById(sysUser));
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
    @PreAuthorize("hasAuthority('user_manage_update')")
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

    /**
     * 查询用户下的菜单列表
     *
     * @return 分页结果
     */
    @GetMapping("/getPrivateMenuDatas")
    @InOutLog("获取用户下的菜单")
    public Result<ElementuiMenuResponse> getPrivateMenuDatas() {
        List<SysPermissionDO> permissionList = null;
        if (RbacUtils.isSuperAdmin(RbacContext.getCurrentUserId())) {
            // 超级管理员
            permissionList = sysPermissionService.selectList(new SysPermissionDO());
        } else {
            Long currentUserId = RbacContext.getCurrentUserId();
            SysUserRoleDO userRoleQuery = new SysUserRoleDO();
            userRoleQuery.setUserId(currentUserId);
            // 查询用户的角色
            List<SysUserRoleDO> userRoleList = sysUserRoleService.selectList(userRoleQuery);
            if (!CollectionUtils.isEmpty(userRoleList)) {
                Set<Long> roleIdSet = userRoleList.stream().map(SysUserRoleDO::getRoleId).collect(Collectors.toSet());
                // 查询角色拥有的权限
                List<SysRolePermissionDO> rolePermissionList = sysRolePermissionService.selectByRoleIdSet(roleIdSet);
                if (!CollectionUtils.isEmpty(rolePermissionList)) {
                    Set<Long> permissionIdSet = rolePermissionList.stream().map(SysRolePermissionDO::getPermissionId)
                        .collect(Collectors.toSet());
                    permissionList = sysPermissionService.selectByIdSet(permissionIdSet, new SysPermissionDO());
                }
            }
        }
        if (CollectionUtils.isEmpty(permissionList)) {
            return Result.success();
        }
        Predicate<SysPermissionDO> predicate = p -> !ResourceType.BUTTON.getValue().equals(p.getResourceType());
        permissionList = permissionList.stream().filter(predicate).collect(Collectors.toList());
        permissionList.sort(Comparator.comparing(AbstractBaseDO::getCreateTime));
        // 将菜单按层级组装
        List<SysPermissionDO> topList =
            permissionList.stream().filter(access -> access.getParentId() == null).collect(Collectors.toList());
        List<ElementuiMenu> menuList = findChildrenMenuCycle(permissionList, topList);
        return Result.success(new ElementuiMenuResponse(menuList));
    }

    /**
     * 递归查找菜单
     *
     * @param allList
     *            所有权限
     * @param childList
     *            当前权限信息
     * @return 菜单列表
     */
    private List<ElementuiMenu> findChildrenMenuCycle(List<SysPermissionDO> allList, List<SysPermissionDO> childList) {
        List<ElementuiMenu> menuList = new ArrayList<>();
        childList.forEach(access -> {
            ElementuiMenu menu =
                ElementuiMenu.builder().index(access.getId().toString()).name(access.getPermissionName())
                    .icon(access.getIcon()).url(access.getUrl()).parentIndex(access.getParentId()).build();
            List<SysPermissionDO> cList =
                allList.stream().filter(acc -> access.getId().equals(acc.getParentId())).collect(Collectors.toList());
            menu.setChildren(findChildrenMenuCycle(allList, cList));
            menuList.add(menu);
        });
        return menuList.size() > 0 ? menuList : null;
    }

}

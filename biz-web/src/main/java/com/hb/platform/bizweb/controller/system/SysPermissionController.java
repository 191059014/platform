package com.hb.platform.bizweb.controller.system;

import com.hb.platform.bizweb.common.Result;
import com.hb.platform.bizweb.common.enums.ResultCode;
import com.hb.platform.bizweb.common.util.HbmanageUtils;
import com.hb.platform.bizweb.container.RbacContext;
import com.hb.platform.bizweb.model.dto.ElementuiMenu;
import com.hb.platform.bizweb.model.dto.ElementuiTree;
import com.hb.platform.bizweb.model.vo.request.PermissionQueryRequest;
import com.hb.platform.bizweb.model.vo.response.ElementuiMenuResponse;
import com.hb.platform.bizweb.model.vo.response.ElementuiTreeResponse;
import com.hb.platform.hbbase.annotation.InOutLog;
import com.hb.platform.hbbase.dao.dobj.base.impl.AbstractBaseDO;
import com.hb.platform.hbbase.model.Page;
import com.hb.platform.hbcommon.validator.Assert;
import com.hb.platform.hbcommon.validator.Check;
import com.hb.platform.hbrbac.dobj.SysPermissionDO;
import com.hb.platform.hbrbac.dobj.SysRolePermissionDO;
import com.hb.platform.hbrbac.dobj.SysUserRoleDO;
import com.hb.platform.hbrbac.enums.ResourceType;
import com.hb.platform.hbrbac.service.ISysPermissionService;
import com.hb.platform.hbrbac.service.ISysRolePermissionService;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
     * 角色权限关系表服务层
     */
    @Resource
    private ISysRolePermissionService sysRolePermissionService;

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
    public Result<Page<SysPermissionDO>> queryPages(@RequestBody PermissionQueryRequest request,
        @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        if (!HbmanageUtils.isSuperAdmin(currentTenantId)) {
            return Result.fail(ResultCode.ACCESS_DENY);
        }
        Assert.ifTrueThrows(Check.incorrectPageParameter(pageNum, pageSize), ResultCode.PAGE_PARAM_ERROR);
        SysPermissionDO query = new SysPermissionDO();
        query.setPermissionName(request.getPermissionName());
        query.setResourceType(request.getResourceType());
        if (request.getTenantId() == null) {
            return Result.success(sysPermissionService.selectPages(query, pageNum, pageSize));
        }
        // 查询商户下所有角色对应的权限
        Set<Long> permissionIdSet = sysRoleService.getPermissionIdSetUnderRoleByTenantId(request.getTenantId());
        if (CollectionUtils.isEmpty(permissionIdSet)) {
            return Result.success();
        }
        return Result.success(sysPermissionService.selectPagesByIdSet(permissionIdSet, query, pageNum, pageSize));
    }

    /**
     * 新增权限信息表
     *
     * @param sysPermission
     *            新增对象信息
     * @return 影响的行数
     */
    @PostMapping("/save")
    @InOutLog("新增权限")
    public Result save(@RequestBody SysPermissionDO sysPermission) {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        if (!HbmanageUtils.isSuperAdmin(currentTenantId)) {
            return Result.fail(ResultCode.ACCESS_DENY);
        }
        Assert.hasText(sysPermission.getPermissionName(), ResultCode.PARAM_ILLEGAL);
        Assert.hasText(sysPermission.getPermissionValue(), ResultCode.PARAM_ILLEGAL);
        Assert.hasText(sysPermission.getResourceType(), ResultCode.PARAM_ILLEGAL);
        return Result.success(sysPermissionService.insert(sysPermission));
    }

    /**
     * 通过主键修改权限信息表
     *
     * @param sysPermission
     *            要修改的信息
     * @return 影响的行数
     */
    @InOutLog("通过主键修改权限")
    @PostMapping("/updateById")
    public Result updateById(@RequestBody SysPermissionDO sysPermission) {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        if (!HbmanageUtils.isSuperAdmin(currentTenantId)) {
            return Result.fail(ResultCode.ACCESS_DENY);
        }
        Assert.notNull(sysPermission.getId(), ResultCode.PARAM_ILLEGAL);
        return Result.success(sysPermissionService.updateById(sysPermission));
    }

    /**
     * 通过主键删除权限信息表
     *
     * @param id
     *            主键
     * @return 影响的行数
     */
    @InOutLog("通过主键删除权限")
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Long id) {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        if (!HbmanageUtils.isSuperAdmin(currentTenantId)) {
            return Result.fail(ResultCode.ACCESS_DENY);
        }
        Assert.notNull(id, ResultCode.PARAM_ILLEGAL);
        return Result.success(sysPermissionService.deleteById(id));
    }

    /**
     * 查询用户下的菜单列表
     *
     * @return 分页结果
     */
    @GetMapping("/getPrivateMenuDatas")
    @InOutLog("获取用户下的菜单")
    public Result<ElementuiMenuResponse> getPrivateMenuDatas() {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        List<SysPermissionDO> permissionList = null;
        if (HbmanageUtils.isSuperAdmin(currentTenantId)) {
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
        Assert.notEmpty(permissionList, ResultCode.NO_DATA);
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

    /**
     * 获取当前用户对应商户下的所有角色的所有权限
     *
     * @return 结果
     */
    @GetMapping("/getPermissionTreeUnderMerchant")
    @InOutLog("获取当前用户对应商户下的所有角色的所有权限")
    public Result<ElementuiTreeResponse> getPermissionTreeUnderMerchant() {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        List<SysPermissionDO> permissionList = null;
        if (HbmanageUtils.isSuperAdmin(currentTenantId)) {
            permissionList = sysPermissionService.selectList(new SysPermissionDO());
        } else {
            // 查询当前商户下的所有角色对应的权限
            permissionList = sysRoleService.getPermissionListUnderRoleByTenantId(currentTenantId);
        }
        Assert.notEmpty(permissionList, ResultCode.NO_DATA);
        List<SysPermissionDO> topList =
            permissionList.stream().filter(access -> access.getParentId() == null).collect(Collectors.toList());
        List<ElementuiTree> treeDataList = findTreeCycle(permissionList, topList);
        return Result.success(new ElementuiTreeResponse(treeDataList));
    }

    /**
     * 递归获取权限树
     *
     * @return 权限树
     */
    private List<ElementuiTree> findTreeCycle(List<SysPermissionDO> allList, List<SysPermissionDO> childList) {
        List<ElementuiTree> treeDataList = new ArrayList<>();
        for (SysPermissionDO access : childList) {
            ElementuiTree treeData =
                ElementuiTree.builder().id(access.getId()).label(access.getPermissionName()).build();
            List<SysPermissionDO> cList =
                allList.stream().filter(acc -> access.getId().equals(acc.getParentId())).collect(Collectors.toList());
            treeData.setChildren(findTreeCycle(allList, cList));
            treeDataList.add(treeData);
        }
        return treeDataList.size() > 0 ? treeDataList : null;
    }

    /**
     * 获取指定角色的所有权限
     *
     * @return 结果
     */
    @InOutLog("获取指定角色的所有权限")
    @GetMapping("/getPermissionsUnderRole")
    public Result<Set<Long>> getPermissionsUnderRole(@RequestParam("roleId") Long roleId) {
        Assert.notNull(roleId, ResultCode.PARAM_ILLEGAL);
        SysRolePermissionDO rolePermissionQuery = new SysRolePermissionDO();
        rolePermissionQuery.setRoleId(roleId);
        List<SysRolePermissionDO> rolePermissionList = sysRolePermissionService.selectList(rolePermissionQuery);
        if (CollectionUtils.isEmpty(rolePermissionList)) {
            return Result.success();
        }
        Set<Long> permissionIdSet =
            rolePermissionList.stream().map(SysRolePermissionDO::getPermissionId).collect(Collectors.toSet());
        // 只返回叶子节点，防止父节点选中，导致子节点全部选中的问题
        List<SysPermissionDO> permissionList =
            sysPermissionService.selectByIdSet(permissionIdSet, new SysPermissionDO());
        Set<Long> parentPermissionIdSet =
            permissionList.stream().map(SysPermissionDO::getParentId).collect(Collectors.toSet());
        permissionIdSet.removeAll(parentPermissionIdSet);
        return Result.success(permissionIdSet);
    }

    /**
     * 通过某商户的某资源类型下的资源
     *
     * @param resourceType
     *            资源类型
     * @return 资源
     */
    @InOutLog("通过资源类型获取当前商户下的资源")
    @GetMapping("/getResourcesByResourceType")
    public Result<List<SysPermissionDO>> getResourcesByResourceType(@RequestParam("resourceType") String resourceType,
        @RequestParam("tenantId") Long tenantId) {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        if (!HbmanageUtils.isSuperAdmin(currentTenantId)) {
            return Result.fail(ResultCode.ACCESS_DENY);
        }
        Assert.notNull(tenantId, ResultCode.PARAM_ILLEGAL);
        Assert.hasText(resourceType, ResultCode.PARAM_ILLEGAL);
        SysPermissionDO query = new SysPermissionDO();
        query.setResourceType(resourceType);
        return Result.success(sysPermissionService.selectList(query));
    }

}

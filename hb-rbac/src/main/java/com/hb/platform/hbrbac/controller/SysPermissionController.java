package com.hb.platform.hbrbac.controller;

import com.hb.platform.hbbase.annotation.InOutLog;
import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.common.ResultCode;
import com.hb.platform.hbbase.model.Page;
import com.hb.platform.hbcommon.validator.Assert;
import com.hb.platform.hbcommon.validator.Check;
import com.hb.platform.hbrbac.RbacContext;
import com.hb.platform.hbrbac.enums.RbacResultCode;
import com.hb.platform.hbrbac.model.dobj.SysPermissionDO;
import com.hb.platform.hbrbac.model.vo.request.PermissionQueryRequest;
import com.hb.platform.hbrbac.service.ISysPermissionService;
import com.hb.platform.hbrbac.service.ISysRoleService;
import com.hb.platform.hbrbac.util.RbacUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

/**
 * 权限信息表控制层
 *
 * @version v0.1, 2021-09-04 12:48:40, create by Mr.Huang.
 */
@RestController
@RequestMapping("/sysPermission")
@Slf4j
public class SysPermissionController {

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
     * 分页查询权限信息表
     *
     * @param request
     *            查询条件对象
     * @param pageNum
     *            当前第几页
     * @param pageSize
     *            每页条数
     * @return 分页结果
     */
    @PreAuthorize("hasAuthority('permission_manage')")
    @PostMapping("/queryPages")
    public Result<Page<SysPermissionDO>> queryPages(@RequestBody PermissionQueryRequest request,
        @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
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
    @PreAuthorize("hasAuthority('permission_manage_add')")
    @PostMapping("/save")
    @InOutLog("新增权限")
    public Result save(@RequestBody SysPermissionDO sysPermission) {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        if (!RbacUtils.isSuperAdmin(currentTenantId)) {
            return Result.fail(RbacResultCode.ACCESS_DENIED);
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
    @PreAuthorize("hasAuthority('permission_manage_update')")
    @InOutLog("通过主键修改权限")
    @PostMapping("/updateById")
    public Result updateById(@RequestBody SysPermissionDO sysPermission) {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        if (!RbacUtils.isSuperAdmin(currentTenantId)) {
            return Result.fail(RbacResultCode.ACCESS_DENIED);
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
    @PreAuthorize("hasAuthority('permission_manage_delete')")
    @InOutLog("通过主键删除权限")
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Long id) {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        if (!RbacUtils.isSuperAdmin(currentTenantId)) {
            return Result.fail(RbacResultCode.ACCESS_DENIED);
        }
        Assert.notNull(id, ResultCode.PARAM_ILLEGAL);
        return Result.success(sysPermissionService.deleteById(id));
    }

    /**
     * 获取某商户的某资源类型下的资源
     *
     * @param resourceType
     *            资源类型
     * @return 资源
     */
    @PreAuthorize("hasAuthority('permission_manage')")
    @InOutLog("通过资源类型获取当前商户下的资源")
    @GetMapping("/getResourcesByResourceType")
    public Result<List<SysPermissionDO>> getResourcesByResourceType(@RequestParam("resourceType") String resourceType) {
        Assert.hasText(resourceType, ResultCode.PARAM_ILLEGAL);
        SysPermissionDO query = new SysPermissionDO();
        query.setResourceType(resourceType);
        return Result.success(sysPermissionService.selectList(query));
    }

}

package com.hb.platform.hbmanage.web.controller.system;

import com.hb.platform.hbmanage.web.common.Result;
import com.hb.platform.hbmanage.web.common.enums.ResultCode;
import com.hb.platform.hbmanage.web.common.util.HbmanageUtils;
import com.hb.platform.hbmanage.web.container.RbacContext;
import com.hb.platform.unic.base.annotation.InOutLog;
import com.hb.platform.unic.base.model.Page;
import com.hb.platform.unic.common.validator.Assert;
import com.hb.platform.unic.common.validator.Check;
import com.hb.platform.unic.rbac.dobj.SysMerchantDO;
import com.hb.platform.unic.rbac.service.ISysMerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商户表控制层
 *
 * @version v0.1, 2021-09-04 12:48:33, create by Mr.Huang.
 */
@RestController
@RequestMapping("/sysMerchant")
public class SysMerchantController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysMerchantController.class);

    /**
     * 商户表服务层
     */
    @Resource
    private ISysMerchantService sysMerchantService;

    /**
     * 分页查询商户表
     *
     * @param sysMerchant
     *            查询条件对象
     * @param pageNum
     *            当前第几页
     * @param pageSize
     *            每页条数
     * @return 分页结果
     */
    @PostMapping("/queryPages")
    @InOutLog("分页查询商户列表")
    public Result<Page<SysMerchantDO>> queryPages(@RequestBody SysMerchantDO sysMerchant,
        @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        Assert.ifTrueThrows(Check.incorrectPageParameter(pageNum, pageSize), ResultCode.PAGE_PARAM_ERROR);
        return Result.success(sysMerchantService.selectPages(sysMerchant, pageNum, pageSize));
    }

    /**
     * 新增商户表
     *
     * @param sysMerchant
     *            新增对象信息
     * @return 影响的行数
     */
    @PostMapping("/save")
    @InOutLog("新增商户")
    public Result save(@RequestBody SysMerchantDO sysMerchant) {
        Assert.hasText(sysMerchant.getMerchantName(), ResultCode.PARAM_ILLEGAL);
        return Result.success(sysMerchantService.insert(sysMerchant));
    }

    /**
     * 通过主键修改商户表
     *
     * @param sysMerchant
     *            要修改的信息
     * @return 影响的行数
     */
    @PostMapping("/updateById")
    @InOutLog("修改商户")
    public Result updateById(@RequestBody SysMerchantDO sysMerchant) {
        Assert.notNull(sysMerchant.getId(), ResultCode.PARAM_ILLEGAL);
        return Result.success(sysMerchantService.updateById(sysMerchant));
    }

    /**
     * 通过主键删除商户表
     *
     * @param id
     *            主键
     * @return 影响的行数
     */
    @GetMapping("/deleteById")
    @InOutLog("删除商户")
    public Result deleteById(@RequestParam("id") Long id) {
        Assert.notNull(id, ResultCode.PARAM_ILLEGAL);
        return Result.success(sysMerchantService.deleteById(id));
    }

    /**
     * 获取所有下级商户
     *
     * @return 结果
     */
    @GetMapping("/getAllSubMerchants")
    @InOutLog("获取所有下级商户")
    public Result<List<SysMerchantDO>> getAllSubMerchants() {
        Long currentTenantId = RbacContext.getCurrentTenantId();
        SysMerchantDO query = new SysMerchantDO();
        if (!HbmanageUtils.isSuperAdmin(currentTenantId)) {
            // 非超级管理员，只能查询用户所在的商户
            query.setId(currentTenantId);
        }
        return Result.success(sysMerchantService.selectList(query));
    }

}

package com.hb.platform.hbrbac.controller;

import com.hb.platform.hbbase.annotation.InOutLog;
import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.common.ResultCode;
import com.hb.platform.hbbase.model.Page;
import com.hb.platform.hbcommon.validator.Assert;
import com.hb.platform.hbcommon.validator.Check;
import com.hb.platform.hbrbac.model.dobj.SysMerchantDO;
import com.hb.platform.hbrbac.service.ISysMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Slf4j
public class SysMerchantController {

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
    @PreAuthorize("hasAuthority('merchant_manage')")
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
    @PreAuthorize("hasAuthority('merchant_manage_add')")
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
    @PreAuthorize("hasAuthority('merchant_manage_update')")
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
    @PreAuthorize("hasAuthority('merchant_manage_delete')")
    @GetMapping("/deleteById")
    @InOutLog("删除商户")
    public Result deleteById(@RequestParam("id") Long id) {
        Assert.notNull(id, ResultCode.PARAM_ILLEGAL);
        SysMerchantDO sysMerchant = new SysMerchantDO();
        sysMerchant.setId(id);
        return Result.success(sysMerchantService.deleteById(sysMerchant));
    }

    /**
     * 获取所有下级商户
     *
     * @return 结果
     */
    @PreAuthorize("hasAuthority('merchant_manage')")
    @GetMapping("/getAllSubMerchants")
    @InOutLog("获取所有下级商户")
    public Result<List<SysMerchantDO>> getAllSubMerchants() {
        return Result.success(sysMerchantService.selectList(new SysMerchantDO()));
    }

}

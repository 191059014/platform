package com.hb.platform.hbbase.controller;

import com.hb.platform.hbbase.annotation.InOutLog;
import com.hb.platform.hbbase.dao.dobj.GlobalConfigDO;
import com.hb.platform.hbbase.service.IGlobalConfigService;
import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.common.ResultCode;
import com.hb.platform.hbbase.model.Page;
import com.hb.platform.hbcommon.validator.Assert;
import com.hb.platform.hbcommon.validator.Check;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 全局配置表控制层
 *
 * @version v0.1, 2021-09-21 15:08:19, create by Mr.Huang.
 */
@Slf4j
@RestController
@RequestMapping("/globalConfig")
public class GlobalConfigController {

    /**
     * 全局配置表服务层
     */
    @Resource
    private IGlobalConfigService globalConfigService;

    /**
     * 分页查询全局配置表
     *
     * @param globalConfig
     *            查询条件对象
     * @param pageNum
     *            当前第几页
     * @param pageSize
     *            每页条数
     * @return 分页结果
     */
    @PostMapping("/queryPages")
    public Result<Page<GlobalConfigDO>> queryPages(@RequestBody GlobalConfigDO globalConfig,
                                                   @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        Assert.ifTrueThrows(Check.incorrectPageParameter(pageNum, pageSize), ResultCode.PAGE_PARAM_ERROR);
        return Result.success(globalConfigService.selectPages(globalConfig, pageNum, pageSize));
    }

    /**
     * 新增全局配置表
     *
     * @param globalConfig
     *            新增对象信息
     * @return 影响的行数
     */
    @InOutLog("新增全局配置表")
    @PostMapping("/save")
    public Result save(@RequestBody GlobalConfigDO globalConfig) {
        return Result.success(globalConfigService.insert(globalConfig));
    }

    /**
     * 通过主键修改全局配置表
     *
     * @param globalConfig
     *            要修改的信息
     * @return 影响的行数
     */
    @InOutLog("通过主键修改全局配置表")
    @PostMapping("/updateById")
    public Result updateById(@RequestBody GlobalConfigDO globalConfig) {
        Assert.notNull(globalConfig.getId(), ResultCode.PARAM_ILLEGAL);
        return Result.success(globalConfigService.updateById(globalConfig));
    }

    /**
     * 通过主键删除全局配置表
     *
     * @param id
     *            主键
     * @return 影响的行数
     */
    @InOutLog("通过主键删除全局配置表")
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Long id) {
        Assert.notNull(id, ResultCode.PARAM_ILLEGAL);
        GlobalConfigDO globalConfig = new GlobalConfigDO();
        globalConfig.setId(id);
        return Result.success(globalConfigService.deleteById(globalConfig));
    }

}

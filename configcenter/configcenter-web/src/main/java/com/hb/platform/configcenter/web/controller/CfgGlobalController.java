package com.hb.platform.configcenter.web.controller;

import com.hb.platform.configcenter.core.dao.dobj.CfgGlobalDO;
import com.hb.platform.configcenter.core.service.ICfgGlobalService;
import com.hb.platform.unic.annotation.InOutLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Resource;

import com.hb.platform.unic.model.Page;
import com.hb.platform.unic.model.Result;

/**
 * 全局配置表控制层
 *
 * @version v0.1, 2021-08-23 22:44:04, create by Mr.Huang.
 */
@RestController
@RequestMapping("/cfgGlobal")
public class CfgGlobalController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CfgGlobalController.class);

    /**
     * 全局配置表服务层
     */
    @Resource
    private ICfgGlobalService cfgGlobalService;

    /**
     * 分页查询全局配置表
     *
     * @param cfgGlobal
     *              查询条件对象
     * @param pageNum
     *              当前第几页
     * @param pageSize
     *              每页条数
     * @return 分页结果
     */
    @PostMapping("/queryPages")
    @InOutLog("分页查询全局配置表")
    public Result<Page<CfgGlobalDO>> queryPages(@RequestBody CfgGlobalDO cfgGlobal,
                                                @RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("pageSize") Integer pageSize) {
        return Result.success(cfgGlobalService.selectPages(cfgGlobal, pageNum, pageSize));
    }

    /**
     * 新增全局配置表
     *
     * @param cfgGlobal
     *            新增对象信息
     * @return 影响的行数
     */
    @PostMapping("/save")
    public Result save(@RequestBody CfgGlobalDO cfgGlobal) {
        return Result.success(cfgGlobalService.insert(cfgGlobal));
    }

    /**
     * 通过主键修改全局配置表
     *
     * @param cfgGlobal
     *            要修改的信息
     * @return 影响的行数
     */
    @PostMapping("/updateById")
    public Result updateById(@RequestBody CfgGlobalDO cfgGlobal) {
        return Result.success(cfgGlobalService.updateById(cfgGlobal));
    }

    /**
     * 通过主键删除全局配置表
     *
     * @param id
     *            主键
     * @return 影响的行数
     */
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Long id) {
        return Result.success(cfgGlobalService.deleteById(id));
    }

}

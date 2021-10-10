package com.hb.platform.bizweb.controller.common;

import com.hb.unic.base.annotation.InOutLog;
import com.hb.unic.base.common.Result;
import com.hb.unic.base.controller.BaseController;
import com.hb.unic.common.standard.Dropdown;
import com.hb.unic.common.util.ExcelUtils;
import com.hb.unic.rbac.common.enums.ResourceType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常用的数据在这里
 *
 * @version v0.1, 2021/9/5 12:22, create by huangbiao.
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController implements InitializingBean {

    /**
     * excel模板头部
     */
    private Map<String, List<String>> excelTemplateHeaders = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        initExcelTemplateHeaders();
    }

    /**
     * 初始化excel模板头部
     */
    private void initExcelTemplateHeaders() {
        excelTemplateHeaders.put("user", Arrays.asList("用户名", "手机号"));
    }

    /**
     * 获取下拉框
     * 
     * @param type
     *            类型
     * @return 结果
     */
    @InOutLog
    @GetMapping("/dropdown/{type}")
    public Result<List<Dropdown>> getDropdownByType(@PathVariable("type") String type) {
        List<Dropdown> list = new ArrayList<>();
        switch (type) {
            case "resourceType":
                Arrays.stream(ResourceType.values())
                    .forEach(obj -> list.add(new Dropdown(obj.getName(), obj.getValue())));
                break;
            default:
                break;
        }
        return Result.success(list);
    }

    /**
     * 下载excel模板
     * 
     * @param type
     *            模板类型
     */
    @InOutLog(printOutLog = false)
    @PostMapping("/excel/template/{type}")
    public void downloadTemplate(@PathVariable("type") String type) {
        ExcelUtils.export(response, type + ExcelUtils.XLS, excelTemplateHeaders.get(type), null);
    }

}

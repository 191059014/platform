package com.hb.platform.bizweb.controller.common;

import com.hb.platform.hbbase.common.Result;
import com.hb.platform.hbbase.common.enums.ErrorType;
import com.hb.platform.hbbase.model.Dropdown;
import com.hb.platform.hbrbac.common.enums.ResourceType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 常用的数据在这里
 *
 * @version v0.1, 2021/9/5 12:22, create by huangbiao.
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    /**
     * 获取下拉框
     * 
     * @param type
     *            类型
     * @return 结果
     */
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

}

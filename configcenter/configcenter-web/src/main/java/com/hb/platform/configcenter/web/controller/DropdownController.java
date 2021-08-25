package com.hb.platform.configcenter.web.controller;

import com.hb.platform.configcenter.web.common.enums.ResultCode;
import com.hb.platform.unic.model.Dropdown;
import com.hb.platform.unic.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 常用的服务
 *
 * @version v0.1, 2021/8/24 21:54, create by huangbiao.
 */
@RestController
@RequestMapping("/dropdown")
public class DropdownController {

    @GetMapping("/{code}")
    public Result<List<Dropdown>> getDropdown(@PathVariable("code") String code) {
        List<Dropdown> list = null;
        switch (code) {
            case "resultcode":
                list = Arrays.stream(ResultCode.values()).map(obj -> new Dropdown(obj.getCode(), obj.getMsg()))
                    .collect(Collectors.toList());
                break;
            default:
                break;
        }
        return Result.success(list);
    }

}

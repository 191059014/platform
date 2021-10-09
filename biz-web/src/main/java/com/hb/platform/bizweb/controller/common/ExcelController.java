package com.hb.platform.bizweb.controller.common;

import com.hb.unic.base.annotation.InOutLog;
import com.hb.unic.base.controller.BaseController;
import com.hb.unic.common.util.ExcelUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel
 *
 * @version v0.1, 2021/10/9 20:38, create by huangbiao.
 */
@RestController
@RequestMapping("/excel")
public class ExcelController extends BaseController {

    private static final Map<String, List<String>> TEMPLATE = new HashMap<>();

    static {
        TEMPLATE.put("user", Arrays.asList("用户名", "手机号"));
    }

    @InOutLog(printOutLog = false)
    @PostMapping("/template/{type}")
    public void downloadTemplate(@PathVariable("type") String type) {
        ExcelUtils.export(response, type + ExcelUtils.XLS, TEMPLATE.get(type), null);
    }

}

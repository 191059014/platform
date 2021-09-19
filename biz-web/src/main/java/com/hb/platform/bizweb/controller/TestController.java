package com.hb.platform.bizweb.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ceshi
 *
 * @version v0.1, 2021/9/18 23:12, create by huangbiao.
 */
@RestController
public class TestController {

    @GetMapping("/test1")
    public String test1() {
        return "访问test1成功";
    }

    @GetMapping("/test2")
    public String test2() {
        return "访问test2成功";
    }

    @GetMapping("/test3")
    @PreAuthorize("hasAnyAuthority('sys_permission')")
    public String test3() {
        return "访问test3成功";
    }

    @GetMapping("/test4")
    public String test4() {
        return "访问test4成功";
    }

}

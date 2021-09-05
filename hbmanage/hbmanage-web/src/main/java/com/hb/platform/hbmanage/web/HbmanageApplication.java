package com.hb.platform.hbmanage.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 启动类
 */
@SpringBootApplication
@ImportResource({"classpath:META-INF/applicationContext.xml"})
public class HbmanageApplication {

    public static void main(String[] args) {
        SpringApplication.run(HbmanageApplication.class, args);
        System.out.println("==================================================");
        System.out.println("  管理后台：http://127.0.0.1:80");
        System.out.println("==================================================");
    }

}

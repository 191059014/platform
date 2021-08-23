package com.hb.platform.rbac.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 启动类
 */
@SpringBootApplication
@ImportResource({"classpath:META-INF/applicationContext.xml"})
public class RbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
        System.out.println("==================================================");
        System.out.println("  用户中心：http://localhost:8082/index.html");
        System.out.println("==================================================");
    }

}

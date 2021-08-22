package com.hb.platform.configcenter.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 启动类
 */
@SpringBootApplication
@ImportResource({"classpath:META-INF/applicationContext.xml"})
public class ConfigcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigcenterApplication.class, args);
        System.out.println("==================================================");
        System.out.println("  配置中心：http://localhost:8081/index.html");
        System.out.println("==================================================");
    }

}

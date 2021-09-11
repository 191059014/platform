package com.hb.platform.bizweb;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 启动类
 */
@SpringBootApplication
@ImportResource({"classpath:META-INF/applicationContext.xml"})
public class BizwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BizwebApplication.class, args);
        System.out.println("==================================================");
        System.out.println("  管理后台：http://127.0.0.1:80");
        System.out.println("==================================================");
    }

}

package com.hb.platform.hbrbac.config;

import com.hb.platform.hbrbac.config.filter.TokenAuthenticationFilter;
import com.hb.platform.hbrbac.config.handler.MyAccessDeniedHandler;
import com.hb.platform.hbrbac.config.handler.MyAuthenticationEntryPoint;
import com.hb.platform.hbrbac.config.handler.MyLoginFailureHandler;
import com.hb.platform.hbrbac.config.handler.MyLoginSuccessHandler;
import com.hb.platform.hbrbac.config.handler.MyLogoutHandler;
import com.hb.platform.hbrbac.config.handler.MyLogoutSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * SpringSecurity配置
 *
 * @author Mr.Huang
 * @version v0.1, 2021/9/15 14:40, create by huangbiao.
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * 用户授权
     */
    @Resource
    private UserDetailsService myUserDetailsService;

    /**
     * 配置
     */
    @Autowired
    private MySercurityConfig mySercurityConfig;

    /**
     * 授权、密码加密
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 禁用csrf
            .cors().and().csrf().disable()
            // 基于token，所以不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // 打开认证配置
            .and().authorizeRequests()
            // 其他所有请求需要身份认证
            .anyRequest().authenticated();

        http
            // 指定表单登陆方式
            .formLogin()
            // 指定登录处理url
            .loginProcessingUrl("/doLogin")
            // 定义登录时的用户名字段
            .usernameParameter("username")
            // 定义登录时的密码字段
            .passwordParameter("password")
            // 登陆成功处理器
            .successHandler(new MyLoginSuccessHandler())
            // 登陆失败处理器
            .failureHandler(new MyLoginFailureHandler())
            // 登录接口直接跳过权限
            .permitAll();

        http
            // 开启注销设置
            .logout()
            // 指定注销处理url
            .logoutUrl("doLogout")
            // 注销处理器
            .addLogoutHandler(new MyLogoutHandler())
            // 注销成功处理器
            .logoutSuccessHandler(new MyLogoutSuccessHandler())
            // 注销接口直接跳过权限
            .permitAll();

        http
            // 开启异常处理
            .exceptionHandling()
            // 认证过的用户访问无权限资源时的异常处理器
            .accessDeniedHandler(new MyAccessDeniedHandler())
            // 匿名用户访问无权限资源时的异常处理器
            .authenticationEntryPoint(new MyAuthenticationEntryPoint());

        // 前后端分离，使用token机制，先进行token认证
        http.addFilterBefore(new TokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略请求，不走过滤器链
        WebSecurity ws = web.ignoring().and();
        log.info("忽略的请求路径: {}", mySercurityConfig.getIgnoreUrls());
        for (String ignoreUrl : mySercurityConfig.getIgnoreUrls()) {
            ws.ignoring().antMatchers(ignoreUrl);
        }
    }
}

package com.hb.platform.bizweb.rbac;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码编码测试类
 *
 * @version v0.1, 2021/4/18 0:26, create by huangbiao.
 */
@Slf4j
public class PasswordEncoderTest {

    @Test
    public void encode() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        log.info(encode);
    }

}

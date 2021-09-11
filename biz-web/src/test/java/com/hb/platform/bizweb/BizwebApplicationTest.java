package com.hb.platform.bizweb;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试启动类
 *
 * @version v0.1, 2021/6/22 9:43, create by huangbiao.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BizwebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BizwebApplicationTest {

    @Before
    public void before() {
        log.info("before test...");
    }

    @After
    public void after() {
        log.info("after test...");
    }

}

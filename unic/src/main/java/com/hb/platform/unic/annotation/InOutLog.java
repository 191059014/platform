package com.hb.platform.unic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 出参入参日志
 *
 * @version v0.1, 2021/8/24 21:13, create by huangbiao.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InOutLog {

    /**
     * 描述
     *
     * @return 描述
     */
    String value() default "";

}

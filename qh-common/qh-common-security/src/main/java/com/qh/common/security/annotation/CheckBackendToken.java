package com.qh.common.security.annotation;

import java.lang.annotation.*;

/**
 * 后端需要校验的接口注解，可以加在类上，也可以加在方法上
 * @author
 * @since 2020/9/2
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckBackendToken {
    boolean required() default true;
}
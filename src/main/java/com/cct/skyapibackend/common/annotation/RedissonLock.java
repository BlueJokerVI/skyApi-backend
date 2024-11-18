package com.cct.skyapibackend.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author cct
 * 分布式注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedissonLock {

    /**
     * key前缀，默认取方法全限定名，可以自己指定
     */
    String prefixKey() default "";

    /**
     * 支持springEl表达式
     */
    String key();

    /**
     * 等待锁的时间，默认不等待
     */
    int waitTime() default -1;

    /**
     *  等待时间单位，默认毫秒
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

}

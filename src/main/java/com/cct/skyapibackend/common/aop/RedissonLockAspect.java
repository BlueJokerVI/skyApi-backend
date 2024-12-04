package com.cct.skyapibackend.common.aop;


import com.cct.skyapibackend.common.annotation.RedissonLock;
import com.cct.skyapibackend.common.service.LockService;
import com.cct.skyapibackend.common.utils.SpringElUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @BelongsProject: skyApi
 * @Author: cct
 * @Description: RedissonLock切面类
 */


@Component
@Aspect
@Order(0) //确保比事务注解先执行（spring事务注解没有标记Order后执行）
public class RedissonLockAspect {
    @Autowired
    private LockService lockService;

    @Around("@annotation(redissonLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String prefixKey = StringUtils.isBlank(redissonLock.prefixKey()) ? SpringElUtils.getMethodKey(method) : redissonLock.prefixKey();
        String key = SpringElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockService.executeWithLock(prefixKey + ":" + key,
                redissonLock.waitTime(),
                redissonLock.unit(),
                () -> {
                    try {
                      return joinPoint.proceed();
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}

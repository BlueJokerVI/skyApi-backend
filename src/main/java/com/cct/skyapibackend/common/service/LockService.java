package com.cct.skyapibackend.common.service;

import com.cct.skyapicommon.domain.enums.RespCodeEnum;
import com.cct.skyapicommon.exception.BusinessException;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @BelongsProject: skyApi
 * @Author: cct
 * @Description: 分布式锁服务类
 */

@Component
public class LockService {
    @Autowired
    private RedissonClient redissonClient;

    @SneakyThrows(InterruptedException.class)
    public <T> T executeWithLock(String key, int waitTime, TimeUnit timeUnit, Supplier<T> supplier)  {
        RLock lock = redissonClient.getLock(key);
        boolean success = lock.tryLock(waitTime, timeUnit);
        if (!success){
            throw new BusinessException(RespCodeEnum.LOCK_LIMIT);
        }

        try{
            return supplier.get();
        }finally {
            lock.unlock();
        }
    }

    @SneakyThrows(InterruptedException.class)
    public void executeWithLock(String key, int waitTime, TimeUnit timeUnit, Runnable runnable) {
        RLock lock = redissonClient.getLock(key);
        boolean success = lock.tryLock(waitTime, timeUnit);
        if (!success){
            throw new BusinessException(RespCodeEnum.LOCK_LIMIT);
        }

        try{
            runnable.run();
        }finally {
            lock.unlock();
        }
    }



    public <T> T executeWithLock(String key ,Supplier<T> supplier)  {
        RLock lock = redissonClient.getLock(key);
        boolean success = lock.tryLock();
        if (!success){
            throw new BusinessException(RespCodeEnum.LOCK_LIMIT);
        }
        try{
            return supplier.get();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 无返回值执行
     * @param key
     * @param runnable
     */
    public void executeWithLock(String key ,Runnable runnable)  {
        RLock lock = redissonClient.getLock(key);
        boolean success = lock.tryLock();
        if (!success){
            throw new BusinessException(RespCodeEnum.LOCK_LIMIT);
        }
        try{
            runnable.run();
        }finally {
            lock.unlock();
        }
    }
}

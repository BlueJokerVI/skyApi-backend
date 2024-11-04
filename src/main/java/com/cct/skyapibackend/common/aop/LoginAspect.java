package com.cct.skyapibackend.common.aop;

import com.cct.skyapibackend.common.annotation.Login;
import com.cct.skyapibackend.common.domain.enums.RespCodeEnum;
import com.cct.skyapibackend.common.utils.ThrowUtils;
import com.cct.skyapibackend.user.domain.vo.UserVo;
import com.cct.skyapibackend.user.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @BelongsProject: skyapibackend
 * @Author: cct
 * @Description: 根据登入状态，配合注解实现接口的访问拦截
 */
@Aspect
@Component
public class LoginAspect {

    @Resource
    private UserService userService;


    @Around("@annotation(login)")
    public Object accessWithNotLogin(ProceedingJoinPoint joinPoint, Login login) throws Throwable {
        boolean allow = login.allow();
        if (!allow) {
            UserVo currentUser = userService.getCurrentUser();
            ThrowUtils.throwIf(currentUser == null, RespCodeEnum.NOT_LOGIN_ERROR);
        }
        return joinPoint.proceed();
    }

}

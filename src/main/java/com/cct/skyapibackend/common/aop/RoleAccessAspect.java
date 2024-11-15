package com.cct.skyapibackend.common.aop;


import com.cct.skyapibackend.common.annotation.RoleAccess;
import com.cct.skyapicommon.domain.enums.RespCodeEnum;
import com.cct.skyapibackend.common.domain.enums.UserRoleEnum;
import com.cct.skyapicommon.utils.ThrowUtils;
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
public class RoleAccessAspect {

    @Resource
    private UserService userService;

    @Around("@annotation(roleAccess)")
    public Object roleAccessAspect(ProceedingJoinPoint joinPoint, RoleAccess roleAccess) throws Throwable {
        UserRoleEnum needRole = roleAccess.role();
        UserVo currentUser = userService.getCurrentUser();
        ThrowUtils.throwIf(needRole.getCode() != currentUser.getUserRole(), RespCodeEnum.NO_AUTH_ERROR);
        return joinPoint.proceed();
    }

}

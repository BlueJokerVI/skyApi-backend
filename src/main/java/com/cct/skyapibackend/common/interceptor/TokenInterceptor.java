package com.cct.skyapibackend.common.interceptor;

import com.cct.skyapibackend.common.domain.dto.RequestInfo;
import com.cct.skyapibackend.common.utils.JwtUtils;
import com.cct.skyapibackend.common.utils.RequestHolderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: Token拦截器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //token格式 ： Bearer token
        String token = extractToken(request);
        Long uidOrNull = jwtUtils.getUidOrNull(token);
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUid(uidOrNull);
        RequestHolderUtils.setValue(requestInfo);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //请求结束后将该次请求连接信息移除
        RequestHolderUtils.remove();
    }

    private static String extractToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7))
                .orElse(null);
    }
}

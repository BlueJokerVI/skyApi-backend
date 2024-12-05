package com.cct.skyapiinterfaceimpl.common.interceptor;


import com.cct.skyapicommon.domain.dto.AKSK;
import com.cct.skyapiinterfaceimpl.common.utils.HttpErrorEnumUtils;
import com.cct.skyapicommon.utils.RedisUtils;
import com.cct.skyapicommon.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.cct.skyapicommon.constant.RedisConstant.NONCE_SET_PREFIX;
import static com.cct.skyapicommon.constant.RedisConstant.USER_AK_SK_PREFIX;


/**
 * 请求认证拦截器
 * 在没有实现网关统一身份认证时，每个项目内需要需要实现认证拦截器
 * @author cct
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ak = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String uid = request.getHeader("uid");

        try {

            //获取分配给用户的ak、sk
            AKSK aksk = RedisUtils.get(USER_AK_SK_PREFIX + uid, AKSK.class);
            if (aksk == null) {
                HttpErrorEnumUtils.ACCESS_DENIED.sendHttpError(response);
                return false;
            }
            String secretKey = aksk.getSecretKey();
            String accessKey = aksk.getAccessKey();


            //是否缺少参数
            if (StringUtils.isEmpty(ak) ||
                    StringUtils.isEmpty(nonce) ||
                    StringUtils.isEmpty(timestamp) ||
                    StringUtils.isEmpty(sign)) {
                HttpErrorEnumUtils.ACCESS_DENIED.sendHttpError(response);
                return false;
            }

            //校验accessKey
            if (!Objects.equals(ak, accessKey)) {
                HttpErrorEnumUtils.ACCESS_DENIED.sendHttpError(response);
                return false;
            }

            //校验随机数是否使用过
            String key = NONCE_SET_PREFIX + nonce;
            if (RedisUtils.hasKey(key)) {
                HttpErrorEnumUtils.ACCESS_DENIED.sendHttpError(response);
                return false;
            }
            //请求过期时间，毫秒
            long timeout = 5 * 60 * 1000;
            RedisUtils.set(key, nonce, timeout, TimeUnit.MILLISECONDS);


            //时间发出时间和当前时间不能超过 5 分钟
            if (System.currentTimeMillis() - Long.parseLong(timestamp) > timeout) {
                HttpErrorEnumUtils.ACCESS_DENIED.sendHttpError(response);
                return false;
            }

            //校验sign是否合法，请求是否被篡改
            if (!SignUtils.verify(sign, SignUtils.getSignContent(request), secretKey)) {
                HttpErrorEnumUtils.ACCESS_DENIED.sendHttpError(response);
                return false;
            }

        } catch (IOException e) {
            log.error("响应头序json列化失败 : ", e);
            throw new RuntimeException(e);
        }

        return true;
    }
}

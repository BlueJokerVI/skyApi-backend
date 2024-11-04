package com.cct.skyapibackend.common.utils;


import com.cct.skyapibackend.common.domain.dto.RequestInfo;

/**
 *
 * @author cct
 * 请求上下文信息
 */
public class RequestHolderUtils {
    private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<>();
    public static void setValue(RequestInfo requestInfo){
        threadLocal.set(requestInfo);
    }

    public static RequestInfo getValue(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }
}

package com.cct.skyapibackend.common.domain.dto;

import lombok.Data;

/**
 * 请求信息封装类，配合CollectorInterceptor收集信息
 * @author cct
 */
@Data
public class RequestInfo {
    Long uid;
}

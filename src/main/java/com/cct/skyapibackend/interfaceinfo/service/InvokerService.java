package com.cct.skyapibackend.interfaceinfo.service;

import com.cct.skyapibackend.common.domain.vo.BaseResponse;
import com.cct.skyapibackend.interfaceinfo.domain.dto.invoke.InvokeReq;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: 调用接口
 * @Version: 1.0
 */
public interface InvokerService{
     Object invoke(InvokeReq invokeReq);
}
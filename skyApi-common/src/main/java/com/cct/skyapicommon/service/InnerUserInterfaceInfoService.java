package com.cct.skyapicommon.service;

import com.cct.skyapicommon.exception.BusinessException;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: 内部  用户接口信息服务
 * @Version: 1.0
 */
public interface InnerUserInterfaceInfoService{


    /**
     * 添加用户调用接口次数
     * @param interfaceId
     * @param userId
     * @return
     */
    Boolean addInvokeTime(Long interfaceId,Long userId) throws BusinessException;
}

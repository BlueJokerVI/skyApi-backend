package com.cct.skyapicommon.service;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: 内部接口信息服务
 * @Version: 1.0
 */
public interface InnerInterfaceInfoService {

    /**
     * 更具url和方method获取接口id
     * @param url
     * @param method
     * @return
     */
    Long getInterfaceInfoId(String url, String method);

}

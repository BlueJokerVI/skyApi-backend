package com.cct.skyapiinterfaceimpl.ipSearch.service;

import com.cct.skyapiinterfaceimpl.ipSearch.model.IPInfo;

/**
 * @author cct
 */
public interface IPService {

    /**
     * 获取ip信息
     * @param ip
     * @return
     */
    IPInfo searchIP(String ip);
}

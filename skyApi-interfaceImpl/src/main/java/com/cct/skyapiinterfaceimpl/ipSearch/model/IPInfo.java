package com.cct.skyapiinterfaceimpl.ipSearch.model;

import lombok.Data;

/**
 * @author cct
 * ip信息包装类
 */
@Data
public class IPInfo {

    /**
     * 国家
     */
    String country;

    /**
     * 地域
     */
    String area;

    /**
     * 运营商
     */
    String net;

    /**
     * ip
     */
    String ip;
}

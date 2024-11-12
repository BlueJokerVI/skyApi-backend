package com.cct.skyapibackend.interfaceinfo.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.cct.skyapibackend.interfaceinfo.domain.entity.InterfaceInfo;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 接口信息表视图
* @author cct
*/
@Data
public class InterfaceInfoVo implements Serializable {

    
    private Long id;
    
    private String name;
    
    private String description;
    
    private String url;
    
    private String requestHeader;
    
    private String responseHeader;
    
    private Integer status;
    
    private String method;
    
    private Long userId;
    
    private Date createTime;
    
    private Date updateTime;

    public static InterfaceInfoVo toVo(InterfaceInfo interfaceInfo) {
        InterfaceInfoVo interfaceInfoVo = new InterfaceInfoVo();
        BeanUtil.copyProperties(interfaceInfo,interfaceInfoVo);
        return interfaceInfoVo;
    }


    private static final long serialVersionUID = 1L;
}
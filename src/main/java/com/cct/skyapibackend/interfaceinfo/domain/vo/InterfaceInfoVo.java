package com.cct.skyapibackend.interfaceinfo.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    private String iconUrl;

    private String requestHeader;
    
    private String responseHeader;
    
    private Integer status;
    
    private String method;
    
    private Long userId;
    
    private Date createTime;
    
    private Date updateTime;

    /**
     * 请求参数
     */
    @TableField(value = "request_param")
    private String requestParam;

    /**
     * 响应参数示例json格式
     */
    private String responseParam;

    /**
     * 响应参数描述,json格式[{name:"",require:false,desc:"",type:""}]
     */
    private String responseParamDesc;

    /**
     * 请求参数描述,json格式[{name:"",require:false,desc:"",type:""}]
     */
    private String requestParamDesc;

    public static InterfaceInfoVo toVo(InterfaceInfo interfaceInfo) {
        InterfaceInfoVo interfaceInfoVo = new InterfaceInfoVo();
        BeanUtil.copyProperties(interfaceInfo,interfaceInfoVo);
        return interfaceInfoVo;
    }


    private static final long serialVersionUID = 1L;
}
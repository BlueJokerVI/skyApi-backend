package com.cct.skyapibackend.interfaceinfo.domain.dto.interfaceinfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author cct
 * @description 接口信息表更新请求
 */
@Data
public class UpdateInterfaceInfoRequest implements Serializable {

    @ApiModelProperty(value = "接口信息记录id")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "接口名")
    @Size(max = 64, message = "长度请在要求范围内")
    private String name;

    @ApiModelProperty(value = "接口描述")
    @Size(max = 255, message = "长度请在要求范围内")
    private String description;

    @ApiModelProperty(value = "接口url")
    @Size(max = 512, message = "长度请在要求范围内")
    private String url;

    @ApiModelProperty(value = "接口url")
    @Size(max = 512, message = "长度请在要求范围内")
    private String iconUrl;


    @ApiModelProperty(value = "接口参数")
    @Size(max = 255, message = "长度请在要求范围内")
    private String requestParam;

    /**
     * 响应参数示例json格式
     */
    @ApiModelProperty(value = "接口响应参数")
    @Size(max = 255, message = "长度请在要求范围内")
    private String responseParam;

    /**
     * 响应参数描述,json格式[{name:"",require:false,desc:"",type:""}]
     */
    @ApiModelProperty(value = "接口响应参数描述")
    @Size(max = 255, message = "长度请在要求范围内")
    private String responseParamDesc;

    /**
     * 请求参数描述,json格式[{name:"",require:false,desc:"",type:""}]
     */
    @ApiModelProperty(value = "接口请求参数描述")
    @Size(max = 255, message = "长度请在要求范围内")
    private String requestParamDesc;

    @ApiModelProperty(value = "接口请求头")
    @Size(max = 8192, message = "长度请在要求范围内")
    private String requestHeader;

    @ApiModelProperty(value = "接口响应头")
    @Size(max = 2048, message = "长度请在要求范围内")
    private String responseHeader;

    @ApiModelProperty(value = "接口状态")
    private Integer status;

    @ApiModelProperty(value = "接口方法")
    @Size(max = 32, message = "长度请在要求范围内")
    private String method;


    private static final long serialVersionUID = 1L;
}
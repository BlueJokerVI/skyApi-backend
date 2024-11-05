package com.cct.skyapibackend.interfaceinfo.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 接口信息表更新请求
* @author cct
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
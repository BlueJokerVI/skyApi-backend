package com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.Long;
import java.lang.Integer;
import java.util.Date;
/**
* @description 用户调用接口信息关系表更新请求
* @author cct
*/
@Data
@ApiModel(description = "用户调用接口信息关系表更新请求")
public class UpdateUserInterfaceInfoRequest implements Serializable {

    @ApiModelProperty(value = "用户调用接口记录id")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "调用总数")
    private Long totalNum;

    @ApiModelProperty(value = "可调用剩余次数")
    private Long remainNum;

    @ApiModelProperty(value = "可调用状态")
    private Integer status;


    private static final long serialVersionUID = 1L;
}
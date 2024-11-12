package com.cct.skyapibackend.interfaceinfo.domain.dto.invoke;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: 调用包亲
 */
@Data
@ApiModel(description = "调用包装类")
public class InvokeReq {

    /**
     * 接口id
     */
    @NotNull
    Long interfaceId;

    /**
     * 请求参数
     */
    @Size(max = 255, message = "长度请在要求范围内")
    String requestParam;
}

package com.cct.skyapibackend.user.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @BelongsProject: skyapibackend
 * @Author: cct
 * @Description: 刷新token请求
 */

@ApiModel(description = "刷新token请求")
@Data
public class RefreshTokenRequest {
    @ApiModelProperty("refreshToken")
    @NotNull
    private String refreshToken;

    @ApiModelProperty("用户id")
    @NotNull
    Long userId;
}

package com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo;

import cn.hutool.core.bean.BeanUtil;
import com.cct.skyapibackend.interfaceinfo.domain.entity.UserInterfaceInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cct
 * @description 用户调用接口信息表添加请求
 */
@Data
@ApiModel(description = "用户调用接口信息表添加请求")
public class AddUserInterfaceInfoRequest implements Serializable {


    @ApiModelProperty(value = "用户id")
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "接口id")
    @NotNull
    private Long interfaceId;


    public UserInterfaceInfo toUserInterfaceInfo() {
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtil.copyProperties(this, userInterfaceInfo);
        return userInterfaceInfo;
    }


    private static final long serialVersionUID = 1L;
}
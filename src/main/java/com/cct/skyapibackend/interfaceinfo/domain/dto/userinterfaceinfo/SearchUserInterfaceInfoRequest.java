package com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo;
import cn.hutool.core.bean.BeanUtil;
import com.cct.skyapibackend.interfaceinfo.domain.entity.UserInterfaceInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.Long;
import java.lang.Integer;
import java.util.Date;
/**
* @description 用户调用接口信息关系表查询请求
* @author cct
*/
@Data
@ApiModel(description = "用户调用接口信息关系表查询请求")
public class SearchUserInterfaceInfoRequest implements Serializable {

    @ApiModelProperty(value = "用户调用接口信息id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "接口id")
    private Long interfaceId;

    @ApiModelProperty(value = "调用总数")
    private Long totalNum;

    @ApiModelProperty(value = "调用剩余次数")
    private Long remainNum;

    @ApiModelProperty(value = "调用剩余次数")
    private Integer status;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    public UserInterfaceInfo toUserInterfaceInfo(){
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtil.copyProperties(this,userInterfaceInfo);
        return userInterfaceInfo;
    }

    private static final long serialVersionUID = 1L;
}
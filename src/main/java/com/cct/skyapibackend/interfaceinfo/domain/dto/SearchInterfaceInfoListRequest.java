package com.cct.skyapibackend.interfaceinfo.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.skyapibackend.common.domain.dto.BasePageReq;
import com.cct.skyapibackend.interfaceinfo.domain.entity.InterfaceInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 接口信息表分页查询请求
* @author cct
*/
@Data
public class SearchInterfaceInfoListRequest extends BasePageReq implements Serializable {


    @ApiModelProperty(value = "接口id")
    private Long id;

    @ApiModelProperty(value = "接口名称")
    private String name;

    @ApiModelProperty(value = "接口描述")
    private String description;

    @ApiModelProperty(value = "接口url")
    private String url;

    @ApiModelProperty(value = "接口请求头")
    private String requestHeader;

    @ApiModelProperty(value = "接口请响应")
    private String responseHeader;

    @ApiModelProperty(value = "接口状态")
    private Integer status;

    @ApiModelProperty(value = "接口方法")
    private String method;

    @ApiModelProperty(value = "接口创建者id")
    private Long userId;

    @ApiModelProperty(value = "接口修改时间")
    private Date updateTime;


    public InterfaceInfo toInterfaceInfo(){
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtil.copyProperties(this,interfaceInfo);
        return interfaceInfo;
    }

    @Override
    public Page<InterfaceInfo> plusPage() {
        return new Page<>(getCurrent(),getPageSize());
    }

    private static final long serialVersionUID = 1L;
}
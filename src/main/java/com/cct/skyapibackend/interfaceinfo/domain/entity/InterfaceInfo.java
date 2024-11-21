package com.cct.skyapibackend.interfaceinfo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName interface_info
 */
@TableName(value = "interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 接口名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 接口描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 接口地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 请求参数
     */
    @TableField(value = "request_param")
    private String requestParam;

    /**
     * 请求头
     */
    @TableField(value = "request_header")
    private String requestHeader;

    /**
     * 响应参数示例json格式
     */
    @TableField(value = "response_param")
    private String responseParam;

    /**
     * 响应参数描述,json格式[{name:"",require:false,desc:"",type:""}]
     */
    @TableField(value = "response_param_desc")
    private String responseParamDesc;

    /**
     * 请求参数描述,json格式[{name:"",require:false,desc:"",type:""}]
     */
    @TableField(value = "request_param_desc")
    private String requestParamDesc;

    /**
     * 响应头
     */
    @TableField(value = "response_header")
    private String responseHeader;

    /**
     * 接口状态 0：可用  1：不可用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 接口方法
     */
    @TableField(value = "method")
    private String method;

    /**
     * 创建者id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除0：删除  1：每删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
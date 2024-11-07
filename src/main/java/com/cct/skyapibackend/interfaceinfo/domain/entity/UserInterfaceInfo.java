package com.cct.skyapibackend.interfaceinfo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user_interface_info
 */
@TableName(value ="user_interface_info")
@Data
public class UserInterfaceInfo implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 调用者id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 被调用接口id
     */
    @TableField(value = "interface_id")
    private Long interfaceId;

    /**
     * 用户调用该接口总次数
     */
    @TableField(value = "total_num")
    private Long totalNum;

    /**
     * 剩余调用次数
     */
    @TableField(value = "remain_num")
    private Long remainNum;

    /**
     * 用户可调用接口状态 0：可用  1：禁用
     */
    @TableField(value = "status")
    private Integer status;

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
     * 是否删除 0：否   1：是
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
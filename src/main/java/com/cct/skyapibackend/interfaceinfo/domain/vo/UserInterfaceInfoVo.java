package com.cct.skyapibackend.interfaceinfo.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.cct.skyapibackend.interfaceinfo.domain.entity.UserInterfaceInfo;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import java.lang.Integer;
import java.util.Date;
/**
* @description 用户调用接口信息关系表视图
* @author cct
*/
@Data
public class UserInterfaceInfoVo implements Serializable {

    
    private Long id;
    
    private Long userId;
    
    private Long interfaceId;
    
    private Long totalNum;
    
    private Long remainNum;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Integer isDelete;

    public static UserInterfaceInfoVo toVo(UserInterfaceInfo userInterfaceInfo) {
        UserInterfaceInfoVo userInterfaceInfoVo = new UserInterfaceInfoVo();
        BeanUtil.copyProperties(userInterfaceInfo,userInterfaceInfoVo);
        return userInterfaceInfoVo;
    }


    private static final long serialVersionUID = 1L;
}
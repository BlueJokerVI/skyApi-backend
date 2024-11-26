package com.cct.skyapibackend.interfaceinfo.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cct.skyapibackend.interfaceinfo.domain.entity.InterfaceInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @description 接口Id和名称信息表
* @author cct
*/
@Data
public class IdNameVo implements Serializable {
    
    private Long id;
    
    private String name;

    public static IdNameVo toVo(InterfaceInfo interfaceInfo) {
        IdNameVo interfaceInfoVo = new IdNameVo();
        BeanUtil.copyProperties(interfaceInfo,interfaceInfoVo);
        return interfaceInfoVo;
    }
    private static final long serialVersionUID = 1L;
}
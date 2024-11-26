package com.cct.skyapibackend.interfaceinfo.dao.mapper;

import com.cct.skyapibackend.interfaceinfo.domain.entity.InterfaceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cct.skyapibackend.interfaceinfo.domain.vo.IdNameVo;

import java.util.List;

/**
* @author cct
* @description 针对表【interface_info】的数据库操作Mapper
* @createDate 2024-11-05 14:06:57
* @Entity generator.domain.entity.InterfaceInfo
*/
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {

    /**
     * 全量查询id和name
     * @return
     */
    List<IdNameVo> getIdAndName();
}





package com.cct.skyapibackend.interfaceinfo.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cct.skyapibackend.interfaceinfo.dao.mapper.InterfaceInfoMapper;
import com.cct.skyapibackend.interfaceinfo.domain.dto.interfaceinfo.SearchInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.entity.InterfaceInfo;
import org.springframework.stereotype.Repository;

/**
* @description 接口信息表数据库操作层
* @author cct
*/
@Repository
public class InterfaceInfoDao extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> {


    public InterfaceInfo searchInterfaceInfo(SearchInterfaceInfoRequest searchInterfaceInfoRequest) {
        return lambdaQuery().select()
                            .eq(searchInterfaceInfoRequest.getId() != null, InterfaceInfo::getId, searchInterfaceInfoRequest.getId())
                            .eq(searchInterfaceInfoRequest.getName() != null, InterfaceInfo::getName, searchInterfaceInfoRequest.getName())
                            .like(searchInterfaceInfoRequest.getDescription() != null, InterfaceInfo::getDescription, searchInterfaceInfoRequest.getDescription())
                            .eq(searchInterfaceInfoRequest.getUrl() != null, InterfaceInfo::getUrl, searchInterfaceInfoRequest.getUrl())
                            .eq(searchInterfaceInfoRequest.getRequestHeader() != null, InterfaceInfo::getRequestHeader, searchInterfaceInfoRequest.getRequestHeader())
                            .eq(searchInterfaceInfoRequest.getResponseHeader() != null, InterfaceInfo::getResponseHeader, searchInterfaceInfoRequest.getResponseHeader())
                            .eq(searchInterfaceInfoRequest.getStatus() != null, InterfaceInfo::getStatus, searchInterfaceInfoRequest.getStatus())
                            .eq(searchInterfaceInfoRequest.getMethod() != null, InterfaceInfo::getMethod, searchInterfaceInfoRequest.getMethod())
                            .eq(searchInterfaceInfoRequest.getUserId() != null, InterfaceInfo::getUserId, searchInterfaceInfoRequest.getUserId())
                            .ge(searchInterfaceInfoRequest.getUpdateTime() != null, InterfaceInfo::getUpdateTime, searchInterfaceInfoRequest.getUpdateTime())
                            .last("limit 1").one();
    }
}





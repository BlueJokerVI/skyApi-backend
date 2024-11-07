package com.cct.skyapibackend.interfaceinfo.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cct.skyapibackend.interfaceinfo.dao.mapper.UserInterfaceInfoMapper;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.SearchUserInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.entity.UserInterfaceInfo;
import org.springframework.stereotype.Repository;

/**
* @description 用户调用接口信息关系表数据库操作层
* @author cct
*/
@Repository
public class UserInterfaceInfoDao extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> {


    public UserInterfaceInfo searchUserInterfaceInfo(SearchUserInterfaceInfoRequest searchUserInterfaceInfoRequest) {
        return lambdaQuery().select()
                            .eq(searchUserInterfaceInfoRequest.getId() != null, UserInterfaceInfo::getId, searchUserInterfaceInfoRequest.getId())
                            .eq(searchUserInterfaceInfoRequest.getUserId() != null, UserInterfaceInfo::getUserId, searchUserInterfaceInfoRequest.getUserId())
                            .eq(searchUserInterfaceInfoRequest.getInterfaceId() != null, UserInterfaceInfo::getInterfaceId, searchUserInterfaceInfoRequest.getInterfaceId())
                            .eq(searchUserInterfaceInfoRequest.getTotalNum() != null, UserInterfaceInfo::getTotalNum, searchUserInterfaceInfoRequest.getTotalNum())
                            .eq(searchUserInterfaceInfoRequest.getRemainNum() != null, UserInterfaceInfo::getRemainNum, searchUserInterfaceInfoRequest.getRemainNum())
                            .eq(searchUserInterfaceInfoRequest.getStatus() != null, UserInterfaceInfo::getStatus, searchUserInterfaceInfoRequest.getStatus())
                            .ge(searchUserInterfaceInfoRequest.getUpdateTime() != null, UserInterfaceInfo::getUpdateTime, searchUserInterfaceInfoRequest.getUpdateTime())
                            .last("limit 1").one();
    }

    public UserInterfaceInfo getByUserIdAndInterfaceId(Long userId,Long interfaceId) {
        return lambdaQuery().select()
                .eq(UserInterfaceInfo::getUserId,userId)
                .eq(UserInterfaceInfo::getInterfaceId,interfaceId)
                .one();
    }
}





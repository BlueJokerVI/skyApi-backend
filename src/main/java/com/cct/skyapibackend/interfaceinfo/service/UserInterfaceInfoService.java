package com.cct.skyapibackend.interfaceinfo.service;

import com.cct.skyapibackend.common.domain.vo.BasePageResp;
import com.cct.skyapibackend.common.domain.vo.BaseResponse;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.AddUserInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.SearchUserInterfaceInfoListRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.SearchUserInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.UpdateUserInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.vo.UserInterfaceInfoVo;

/**
 * @description 用户调用接口信息关系表服务层
 * @author cct
 */
public interface UserInterfaceInfoService {

    /**
     * 添加用户调用接口信息关系表记录
     * @param addUserInterfaceInfoRequest
     * @return
     */
    BaseResponse<UserInterfaceInfoVo> addUserInterfaceInfo(AddUserInterfaceInfoRequest addUserInterfaceInfoRequest);

    BaseResponse<Void> deleteUserInterfaceInfo(Long questionId);

    /**
     * 更新用户调用接口信息关系表记录
     * @param updateUserInterfaceInfoRequest
     * @return
     */
    BaseResponse<UserInterfaceInfoVo> updateUserInterfaceInfo(UpdateUserInterfaceInfoRequest updateUserInterfaceInfoRequest);

    /**
     * 查询用户调用接口信息关系表记录
     * @param searchUserInterfaceInfoRequest
     * @return
     */
    BaseResponse<UserInterfaceInfoVo> searchUserInterfaceInfo(SearchUserInterfaceInfoRequest searchUserInterfaceInfoRequest);

    /**
     * 分页查询用户调用接口信息关系表记录
     * @param searchUserInterfaceInfoListRequest
     * @return
     */
    BaseResponse<BasePageResp<UserInterfaceInfoVo>> searchUserInterfaceInfoPage(SearchUserInterfaceInfoListRequest searchUserInterfaceInfoListRequest);
}

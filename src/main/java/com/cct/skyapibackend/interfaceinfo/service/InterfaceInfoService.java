package com.cct.skyapibackend.interfaceinfo.service;

import com.cct.skyapibackend.common.domain.vo.BasePageResp;
import com.cct.skyapibackend.common.domain.vo.BaseResponse;
import com.cct.skyapibackend.interfaceinfo.domain.dto.AddInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.SearchInterfaceInfoListRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.SearchInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.UpdateInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.vo.InterfaceInfoVo;

/**
 * @description 接口信息表服务层
 * @author cct
 */
public interface InterfaceInfoService {

    /**
     * 添加接口信息表
     * @param addInterfaceInfoRequest
     * @return
     */
    BaseResponse<InterfaceInfoVo> addInterfaceInfo(AddInterfaceInfoRequest addInterfaceInfoRequest);

    /**
     * 删除接口信息记录
     * @param questionId
     * @return
     */
    BaseResponse<Void> deleteInterfaceInfo(Long questionId);

    /**
     * 修改接口信息记录
     * @param updateInterfaceInfoRequest
     * @return
     */
    BaseResponse<InterfaceInfoVo> updateInterfaceInfo(UpdateInterfaceInfoRequest updateInterfaceInfoRequest);

    /**
     * 查询接口
     * @param searchInterfaceInfoRequest
     * @return
     */
    BaseResponse<InterfaceInfoVo> searchInterfaceInfo(SearchInterfaceInfoRequest searchInterfaceInfoRequest);

    /**
     * 翻页查询接口
     * @param searchInterfaceInfoListRequest
     * @return
     */
    BaseResponse<BasePageResp<InterfaceInfoVo>> searchInterfaceInfoPage(SearchInterfaceInfoListRequest searchInterfaceInfoListRequest);
}

package com.cct.skyapibackend.interfaceinfo.controller;

import com.cct.skyapibackend.common.domain.vo.BasePageResp;
import com.cct.skyapicommon.domain.vo.BaseResponse;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.AddUserInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.SearchUserInterfaceInfoListRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.SearchUserInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.UpdateUserInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.vo.UserInterfaceInfoVo;
import com.cct.skyapibackend.interfaceinfo.service.UserInterfaceInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
/**
 * @description 用户调用接口信息关系表控制器
 * @author cct
 */
@RestController
@RequestMapping("/userInterfaceInfo")
public class UserInterfaceInfoController {

    @Resource
    UserInterfaceInfoService userInterfaceInfoService;



    @PostMapping("/add")
    BaseResponse<UserInterfaceInfoVo>  addUserInterfaceInfo(@Valid @RequestBody AddUserInterfaceInfoRequest addUserInterfaceInfoRequest){
        return userInterfaceInfoService.addUserInterfaceInfo(addUserInterfaceInfoRequest);
    }

    @GetMapping("/delete")
    BaseResponse<Void>  deleteUserInterfaceInfo(@Valid @NotNull @RequestParam Long userInterfaceInfoId){
        return userInterfaceInfoService.deleteUserInterfaceInfo(userInterfaceInfoId);
    }

    @PostMapping("/update")
    BaseResponse<UserInterfaceInfoVo>  updateUserInterfaceInfo(@Valid @RequestBody UpdateUserInterfaceInfoRequest updateUserInterfaceInfoRequest){
        return userInterfaceInfoService.updateUserInterfaceInfo(updateUserInterfaceInfoRequest);
    }

    @PostMapping("/search")
    BaseResponse<UserInterfaceInfoVo>  searchUserInterfaceInfo(@Valid @RequestBody SearchUserInterfaceInfoRequest searchUserInterfaceInfoRequest){
        return userInterfaceInfoService.searchUserInterfaceInfo(searchUserInterfaceInfoRequest);
    }

    @PostMapping("/searchPage")
    BaseResponse<BasePageResp<UserInterfaceInfoVo>> searchUserInterfaceInfoPage(@Valid @RequestBody SearchUserInterfaceInfoListRequest searchUserInterfaceInfoListRequest){
        return userInterfaceInfoService.searchUserInterfaceInfoPage(searchUserInterfaceInfoListRequest);
    }

}

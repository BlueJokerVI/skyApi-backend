package com.cct.skyapibackend.interfaceinfo.controller;

import com.cct.skyapibackend.common.annotation.RoleAccess;
import com.cct.skyapibackend.common.domain.enums.UserRoleEnum;
import com.cct.skyapibackend.common.domain.vo.BasePageResp;

import com.cct.skyapibackend.interfaceinfo.domain.dto.interfaceinfo.AddInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.interfaceinfo.SearchInterfaceInfoListRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.interfaceinfo.SearchInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.interfaceinfo.UpdateInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.vo.IdNameVo;
import com.cct.skyapibackend.interfaceinfo.domain.vo.InterfaceInfoVo;
import com.cct.skyapibackend.interfaceinfo.service.InterfaceInfoService;
import com.cct.skyapicommon.domain.vo.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description 接口信息表控制器
 * @author cct
 */
@RestController
@RequestMapping("/interfaceInfo")
@Api(tags = "接口控制器")
public class InterfaceInfoController {

    @Resource
    InterfaceInfoService interfaceInfoService;

    @GetMapping("/online")
    @ApiOperation("接口上线")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<Void>  onlineInterfaceInfo(@Valid @NotNull @RequestParam Long interfaceInfoId){
        return interfaceInfoService.onlineInterfaceInfo(interfaceInfoId);
    }

    @GetMapping("/offline")
    @ApiOperation("接口下线")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<Void> offlineInterfaceInfo(@Valid @NotNull @RequestParam Long interfaceInfoId){
        return interfaceInfoService.offlineInterfaceInfo(interfaceInfoId);
    }


    @PostMapping("/add")
    @ApiOperation("接口添加")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<InterfaceInfoVo>  addInterfaceInfo(@Valid @RequestBody AddInterfaceInfoRequest addInterfaceInfoRequest){
        return interfaceInfoService.addInterfaceInfo(addInterfaceInfoRequest);
    }

    @GetMapping("/delete")
    @ApiOperation("接口删除")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<Void>  deleteInterfaceInfo(@Valid @NotNull @RequestParam Long interfaceInfoId){
        return interfaceInfoService.deleteInterfaceInfo(interfaceInfoId);
    }

    @PostMapping("/update")
    @ApiOperation("接口更新")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<InterfaceInfoVo>  updateInterfaceInfo(@Valid @RequestBody UpdateInterfaceInfoRequest updateInterfaceInfoRequest){
        return interfaceInfoService.updateInterfaceInfo(updateInterfaceInfoRequest);
    }

    @PostMapping("/search")
    @ApiOperation("接口查询")
    BaseResponse<InterfaceInfoVo>  searchInterfaceInfo(@Valid @RequestBody SearchInterfaceInfoRequest searchInterfaceInfoRequest){
        return interfaceInfoService.searchInterfaceInfo(searchInterfaceInfoRequest);
    }

    @GetMapping("/IdAndName/search")
    @ApiOperation("接口查询IdNames")
    BaseResponse<List<IdNameVo>>  idAndNameSearch(){
        return interfaceInfoService.idAndNameSearch();
    }

    @PostMapping("/searchPage")
    @ApiOperation("接口分页查询")
    BaseResponse<BasePageResp<InterfaceInfoVo>> searchInterfaceInfoPage(@Valid @RequestBody SearchInterfaceInfoListRequest searchInterfaceInfoListRequest){
        return interfaceInfoService.searchInterfaceInfoPage(searchInterfaceInfoListRequest);
    }

}


package com.cct.skyapibackend.interfaceinfo.controller;

import com.cct.skyapibackend.common.domain.vo.BasePageResp;
import com.cct.skyapibackend.common.domain.vo.BaseResponse;
import com.cct.skyapibackend.interfaceinfo.domain.dto.AddInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.SearchInterfaceInfoListRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.SearchInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.UpdateInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.vo.InterfaceInfoVo;
import com.cct.skyapibackend.interfaceinfo.service.InterfaceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @PostMapping("/add")
    @ApiOperation("接口添加")
    BaseResponse<InterfaceInfoVo>  addInterfaceInfo(@Valid @RequestBody AddInterfaceInfoRequest addInterfaceInfoRequest){
        return interfaceInfoService.addInterfaceInfo(addInterfaceInfoRequest);
    }

    @GetMapping("/delete")
    @ApiOperation("接口删除")
    BaseResponse<Void>  deleteInterfaceInfo(@Valid @NotNull @RequestParam Long interfaceInfoId){
        return interfaceInfoService.deleteInterfaceInfo(interfaceInfoId);
    }

    @PostMapping("/update")
    @ApiOperation("接口更新")
    BaseResponse<InterfaceInfoVo>  updateInterfaceInfo(@Valid @RequestBody UpdateInterfaceInfoRequest updateInterfaceInfoRequest){
        return interfaceInfoService.updateInterfaceInfo(updateInterfaceInfoRequest);
    }

    @PostMapping("/search")
    @ApiOperation("接口查询")
    BaseResponse<InterfaceInfoVo>  searchInterfaceInfo(@Valid @RequestBody SearchInterfaceInfoRequest searchInterfaceInfoRequest){
        return interfaceInfoService.searchInterfaceInfo(searchInterfaceInfoRequest);
    }

    @PostMapping("/searchPage")
    @ApiOperation("接口分页查询")
    BaseResponse<BasePageResp<InterfaceInfoVo>> searchInterfaceInfoPage(@Valid @RequestBody SearchInterfaceInfoListRequest searchInterfaceInfoListRequest){
        return interfaceInfoService.searchInterfaceInfoPage(searchInterfaceInfoListRequest);
    }

}


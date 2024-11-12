package com.cct.skyapibackend.interfaceinfo.controller;

import com.cct.skyapibackend.common.annotation.RoleAccess;
import com.cct.skyapibackend.common.domain.enums.UserRoleEnum;
import com.cct.skyapibackend.common.domain.vo.BaseResponse;
import com.cct.skyapibackend.common.utils.RespUtils;
import com.cct.skyapibackend.interfaceinfo.domain.dto.invoke.InvokeReq;
import com.cct.skyapiclientsdk.client.ApiClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: 调用接口控制器
 */
@RestController
@RequestMapping("/invoke")
@Api(tags = "调用接口控制器")
public class InvokeController {


    @Resource
    private ApiClient apiClient;

    @PostMapping
    @ApiOperation("接口调用")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<Object> invoke(@Valid @RequestBody InvokeReq invokeReq) {
        String res = apiClient.post(invokeReq.getRequestParam());
        return RespUtils.success(res);
    }

}

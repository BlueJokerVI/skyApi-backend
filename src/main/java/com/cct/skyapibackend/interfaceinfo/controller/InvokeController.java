package com.cct.skyapibackend.interfaceinfo.controller;

import com.cct.skyapibackend.common.annotation.Login;
import com.cct.skyapibackend.interfaceinfo.domain.dto.invoke.InvokeReq;
import com.cct.skyapibackend.interfaceinfo.service.InvokerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
    private InvokerService invokerService;

    @PostMapping
    @ApiOperation("接口调用")
    @Login
    String invoke(@Valid @RequestBody InvokeReq invokeReq, HttpServletResponse response) {
        //给响应头不拦截标准，方便前端处理,并且允许其暴露
        response.addHeader("No-Intercept", "ctc");
        // 添加 CORS 相关的响应头，确保前端可以读取到自定义头部
        response.addHeader("Access-Control-Expose-Headers", "No-Intercept");
        return invokerService.invoke(invokeReq);
    }

}

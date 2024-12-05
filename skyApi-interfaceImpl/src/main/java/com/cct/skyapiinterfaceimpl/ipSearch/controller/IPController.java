package com.cct.skyapiinterfaceimpl.ipSearch.controller;

import com.cct.skyapicommon.domain.vo.BaseResponse;
import com.cct.skyapicommon.utils.RespUtils;
import com.cct.skyapiinterfaceimpl.ipSearch.model.IPInfo;
import com.cct.skyapiinterfaceimpl.ipSearch.service.IPService;
import com.cct.skyapiinterfaceimpl.testInterface.model.PostReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: cct
 * ip 查询
 */
@RestController
@RequestMapping("/")
@Api(tags = "IP控制器")
@Slf4j
public class IPController {

    @Resource
    private IPService ipService;

    @ApiOperation("解析IP")
    @GetMapping("/ip")
    public BaseResponse<IPInfo> get( String ip) {
        log.info("解析ip {}",ip);
        IPInfo ipInfo = ipService.searchIP(ip);
        return RespUtils.success(ipInfo);
    }

}

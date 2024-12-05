package com.cct.skyapiinterfaceimpl.bloodType.controller;

import com.cct.skyapicommon.domain.vo.BaseResponse;
import com.cct.skyapicommon.utils.RespUtils;
import com.cct.skyapiinterfaceimpl.bloodType.model.BloodTypePossible;
import com.cct.skyapiinterfaceimpl.bloodType.service.CalculateBloodTypeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "血型计算")
@RequestMapping("/blood")
public class BloodTypeController {

    @Resource
    private CalculateBloodTypeService calculateBloodTypeService;

    @GetMapping("/get")
    public BaseResponse<BloodTypePossible> get(String father, String mother) {
        BloodTypePossible maybeBloodType = calculateBloodTypeService.getMaybeBloodType(father, mother);
        return RespUtils.success(maybeBloodType);
    }

}
package com.cct.skyapiinterfaceimpl.testInterface;

import com.cct.skyapicommon.domain.vo.BaseResponse;
import com.cct.skyapicommon.utils.RespUtils;
import com.cct.skyapiinterfaceimpl.testInterface.model.PostReq;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: 具体实现接口功能模块
 */
@RestController
@Api(tags = "测试控制器")
@RequestMapping("/test")
public class TestController {

    @GetMapping("/get")
    public BaseResponse<String> get( String number) {
        System.out.println("test");
        return RespUtils.success("Get类型参数 :" + number);
    }


    @PostMapping("/post")
    public BaseResponse<String>  post(@RequestBody PostReq param) {
        System.out.println("test");
        return RespUtils.success("Post类型参数：" + param.getName() + " " + param.getAge());
    }

}

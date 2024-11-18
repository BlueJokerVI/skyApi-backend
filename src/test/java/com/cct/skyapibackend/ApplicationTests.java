package com.cct.skyapibackend;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.interfaces.Claim;
import com.cct.skyapibackend.common.annotation.RedissonLock;
import com.cct.skyapibackend.common.utils.JwtUtils;
import com.cct.skyapibackend.interfaceinfo.service.inner.InnerInterfaceInfoServiceImpl;
import com.cct.skyapicommon.service.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;


@SpringBootTest
@EnableAspectJAutoProxy(proxyTargetClass = true)
class ApplicationTests {

    @Resource
    private InnerInterfaceInfoServiceImpl innerInterfaceInfoService;

    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Test
    void test(){
        innerUserInterfaceInfoService.addInvokeTime(1853693514501070848L,1854793399153057794L);
    }

}

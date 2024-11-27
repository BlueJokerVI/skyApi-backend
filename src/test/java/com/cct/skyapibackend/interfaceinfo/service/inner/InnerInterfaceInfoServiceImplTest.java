package com.cct.skyapibackend.interfaceinfo.service.inner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InnerInterfaceInfoServiceImplTest {

    @Resource
    private InnerInterfaceInfoServiceImpl  innerInterfaceInfoService;

    @Test
    public void test(){
        Long interfaceInfoId = innerInterfaceInfoService.getInterfaceInfoId("http://localhost:8080/api/ip","GET");
        System.out.println();
    }
}
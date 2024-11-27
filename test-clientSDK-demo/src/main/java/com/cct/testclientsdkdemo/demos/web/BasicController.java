/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cct.testclientsdkdemo.demos.web;

import com.cct.skyapiclientsdk.client.ApiClient;
import com.cct.skyapiclientsdk.model.req.GetReq;
import com.cct.skyapiclientsdk.model.req.IPReq;
import com.cct.skyapiclientsdk.model.resp.BaseResponse;
import com.cct.skyapiclientsdk.model.resp.IPInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BasicController {

    @Resource
    private ApiClient apiClient;

    @GetMapping("/get")
    public BaseResponse<IPInfo> testSDK() {
        IPReq req = new IPReq();
        req.setIp("203.15.246.0");
        BaseResponse<IPInfo> ipInfo = apiClient.getIpInfo(req);
        System.out.println(ipInfo);
        return ipInfo;
    }

}

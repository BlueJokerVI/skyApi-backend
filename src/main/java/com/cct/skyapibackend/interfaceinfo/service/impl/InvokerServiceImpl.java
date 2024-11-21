package com.cct.skyapibackend.interfaceinfo.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.cct.skyapibackend.common.domain.enums.HttpMethod;
import com.cct.skyapibackend.interfaceinfo.dao.InterfaceInfoDao;
import com.cct.skyapibackend.interfaceinfo.domain.dto.invoke.InvokeReq;
import com.cct.skyapibackend.interfaceinfo.domain.entity.InterfaceInfo;
import com.cct.skyapibackend.interfaceinfo.domain.enums.InterfaceStatusEnum;
import com.cct.skyapibackend.interfaceinfo.service.InvokerService;
import com.cct.skyapibackend.user.domain.vo.UserVo;
import com.cct.skyapibackend.user.service.UserService;
import com.cct.skyapicommon.domain.enums.RespCodeEnum;
import com.cct.skyapicommon.domain.vo.BaseResponse;
import com.cct.skyapicommon.exception.BusinessException;
import com.cct.skyapicommon.utils.JsonUtils;
import com.cct.skyapicommon.utils.SignUtils;
import com.cct.skyapicommon.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: TODO
 */

@Service
@Slf4j
public class InvokerServiceImpl implements InvokerService {

    @Resource
    private UserService userService;

    @Resource
    private InterfaceInfoDao interfaceInfoDao;

    @Override
    public String invoke(InvokeReq invokeReq) {
        // 1.获取当前用户ak ，sk
        // 2.获取要调用接口信息
        // 3.封装调用接口请求
        // 4.调用接口返回结果
        UserVo currentUser = userService.getCurrentUser();
        String accessKey = currentUser.getAccessKey();
        String secretKey = currentUser.getSecretKey();


        if (StrUtil.isBlank(accessKey) || StrUtil.isBlank(secretKey)) {
            log.error("系统错误用户id: {} ,ak 或 sk 不存在", currentUser.getId());
            throw new BusinessException(RespCodeEnum.SYSTEM_ERROR, "系统错误ak 或 sk 不存在");
        }


        InterfaceInfo interfaceInfo = interfaceInfoDao.getById(invokeReq.getInterfaceId());
        ThrowUtils.throwIf(interfaceInfo == null, RespCodeEnum.PARAMS_ERROR, "接口不存在");
        ThrowUtils.throwIf(interfaceInfo.getStatus().equals(InterfaceStatusEnum.STOP.getCode()),RespCodeEnum.PARAMS_ERROR,"接口不可用");

        String url = interfaceInfo.getUrl();
        String method = interfaceInfo.getMethod();

        //获取请求同需要添加的参数用于api签名
        Map<String, List<String>> headers = getHeaders(accessKey, currentUser.getId().toString());
        String uri = URLUtil.getPath(url);
        String param = invokeReq.getRequestParam();
        String res;
        if (method.equalsIgnoreCase(HttpMethod.POST.getDesc())) {
            //post请求

            //计算sign

            String content = uri + "\n" + param;
            String sign = SignUtils.sign(content, secretKey);
            headers.put("sign", Collections.singletonList(sign));

            HttpResponse response = HttpRequest.post(url)
                    .header(headers)
                    .body(param)
                    .execute();

            res = response.body();
        } else {
            //get请求
            Map<String, Object> params = parseQueryString(param);
            String content = uri + "\n" + param;
            String sign = SignUtils.sign(content, secretKey);
            headers.put("sign", Collections.singletonList(sign));
            HttpResponse response = HttpRequest.get(url)
                    .header(headers)
                    .form(params)
                    .execute();

            res = response.body();
        }
        return res;
    }


    private Map<String, List<String>> getHeaders(String accessKey, String uid) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("accessKey", Collections.singletonList(accessKey));
        headers.put("nonce", Collections.singletonList(IdUtil.fastSimpleUUID()));
        headers.put("timestamp", Collections.singletonList(System.currentTimeMillis() + ""));
        headers.put("uid", Collections.singletonList(uid));
        return headers;
    }


    /**
     * 处理get请求参数  将a=1&b=2的参数转为Map类型
     *
     * @param query
     * @return
     */
    public Map<String, Object> parseQueryString(String query) {
        Map<String, Object> params = new HashMap<>();

        // 分割每个键值对
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            // 分割键和值
            String[] keyValue = pair.split("=", 2);
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : "";

            params.put(key, value);
        }

        return params;
    }
}

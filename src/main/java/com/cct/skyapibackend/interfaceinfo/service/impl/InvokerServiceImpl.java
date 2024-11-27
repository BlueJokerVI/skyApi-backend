package com.cct.skyapibackend.interfaceinfo.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.cct.skyapibackend.common.domain.enums.HttpMethod;
import com.cct.skyapibackend.interfaceinfo.dao.InterfaceInfoDao;
import com.cct.skyapibackend.interfaceinfo.domain.dto.invoke.InvokeReq;
import com.cct.skyapibackend.interfaceinfo.domain.entity.InterfaceInfo;
import com.cct.skyapibackend.interfaceinfo.domain.enums.InterfaceStatusEnum;
import com.cct.skyapibackend.interfaceinfo.service.InvokerService;
import com.cct.skyapibackend.user.domain.vo.UserVo;
import com.cct.skyapibackend.user.service.UserService;
import com.cct.skyapicommon.domain.enums.RespCodeEnum;
import com.cct.skyapicommon.exception.BusinessException;
import com.cct.skyapicommon.utils.SignUtils;
import com.cct.skyapicommon.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

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
        ThrowUtils.throwIf(interfaceInfo.getStatus().equals(InterfaceStatusEnum.STOP.getCode()), RespCodeEnum.PARAMS_ERROR, "接口不可用");

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
            param = sorted(param);
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
     * 解析查询字符串并对每个键值对进行 URL 编码
     *
     * @param query 查询字符串，格式如 key1=value1&key2=value2
     * @return 解析后的键值对 Map
     */
    private static Map<String, Object> parseQueryString(String query) {
        Map<String, Object> params = new HashMap<>();

        if (query == null || query.trim().isEmpty()) {
            // 如果查询字符串为空或 null，则直接返回空 Map
            return params;
        }

        // 分割每个键值对
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            // 分割键和值
            String[] keyValue = pair.split("=", 2);
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : "";

            try {
                // 对键和值进行 URL 编码
                key = URLEncoder.encode(key, "UTF-8");
                value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // 如果编码失败，打印错误并保留原始值
                System.err.println("URL 编码失败: " + e.getMessage());
            }
            params.put(key, value);
        }
        return params;
    }

    /**
     * 对b=1&a=2按key的字典序进行排序并返回  --->   a=2&b=1
     *
     * @param query
     * @return
     */
    private static String sorted(String query) {
        String[] split = query.split("&");
        Arrays.sort(split, (o1, o2) -> {
            String[] split1 = o1.split("=");
            String[] split2 = o2.split("=");
            return split1[0].compareTo(split2[0]);
        });

        return String.join("&", split);
    }

}

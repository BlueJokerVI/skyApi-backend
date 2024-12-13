package com.cct.skyapigateway;

import com.cct.skyapicommon.domain.dto.AKSK;
import com.cct.skyapicommon.service.InnerInterfaceInfoService;
import com.cct.skyapicommon.service.InnerUserInterfaceInfoService;
import com.cct.skyapicommon.utils.RedisUtils;
import com.cct.skyapicommon.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.image.DataBufferByte;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.cct.skyapicommon.constant.RedisConstant.NONCE_SET_PREFIX;
import static com.cct.skyapicommon.constant.RedisConstant.USER_AK_SK_PREFIX;

/**
 * 全局请求过滤器
 *
 * @author cct
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final String INTERFACE_HOST = "http://localhost:8889";


    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = Objects.requireNonNull(request.getMethod()).toString();
        //获取不带参数的url
        String url = request.getURI().toString();
        int existParam = url.lastIndexOf('?');
        url = existParam == -1 ? url : url.substring(0, existParam);

        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        log.info("请求方法：" + method);
        if (method.equals("GET")) {
            log.info("GET请求请求参数：" + request.getQueryParams());
        }
        String sourceAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        log.info("请求来源地址：" + sourceAddress);
        log.info("请求来源地址：" + request.getRemoteAddress());
        ServerHttpResponse response = exchange.getResponse();

        //3. 用户鉴权（判断 ak、sk 是否合法）
        HttpHeaders headers = request.getHeaders();
        String ak = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String uid = headers.getFirst("uid");

        //获取分配给用户的ak、sk
        AKSK aksk = RedisUtils.get(USER_AK_SK_PREFIX + uid, AKSK.class);
        if (aksk == null) {
            return handleNoAuth(response);
        }
        String secretKey = aksk.getSecretKey();
        String accessKey = aksk.getAccessKey();

        //是否缺少参数
        if (!StringUtils.hasLength(ak) ||
                !StringUtils.hasLength(nonce) ||
                !StringUtils.hasLength(timestamp) ||
                !StringUtils.hasLength(sign)) {
            return handleNoAuth(response);
        }

        //校验accessKey
        if (!Objects.equals(ak, accessKey)) {
            return handleNoAuth(response);
        }

        //校验随机数是否使用过
        String key = NONCE_SET_PREFIX + nonce;
        if (RedisUtils.hasKey(key)) {

            return handleNoAuth(response);
        }
        //请求过期时间，毫秒
        long timeout = 5 * 60 * 1000;
        RedisUtils.set(key, nonce, timeout, TimeUnit.MILLISECONDS);

        //时间发出时间和当前时间不能超过 5 分钟
        if (System.currentTimeMillis() - Long.parseLong(timestamp) > timeout) {
            return handleNoAuth(response);
        }
        //校验sign是否合法，请求是否被篡改
        String uri = request.getURI().getPath();
        // post与get请求的不同处理，sign的内容是有 uri + \n + 请求参数 组成
        String post = "POST";
        if (post.equalsIgnoreCase(Objects.requireNonNull(exchange.getRequest().getMethod()).name())) {
            // 读取请求体并缓存
            String finalUrl = url;
            //当body中没有缓存时，只会执行这一个拦截器， 原因是fileMap中的代码没有执行，所以需要在波多野为空时构建一个空的缓存
            DefaultDataBufferFactory defaultDataBufferFactory = new DefaultDataBufferFactory();
            DefaultDataBuffer defaultDataBuffer = defaultDataBufferFactory.allocateBuffer(0);
            return DataBufferUtils.join(exchange.getRequest().getBody())
                    .defaultIfEmpty(defaultDataBuffer)
                    .flatMap(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        DataBufferUtils.release(dataBuffer); // 释放原始 DataBuffer
                        String requestBody = new String(bytes, StandardCharsets.UTF_8);
                        log.info("POST请求请求参数：{}", requestBody);
                        // 这里是用于签名校验
                        String context = uri + "\n" + requestBody;
                        if (!Objects.equals(sign, SignUtils.sign(context, secretKey))) {
                            return handleNoAuth(response);
                        }

                        // 创建新的 DataBuffer 并将 JSON 字符串重新写入其中
                        DataBuffer cachedDataBuffer = new DefaultDataBufferFactory().wrap(bytes);

                        // 创建新的 ServerHttpRequestDecorator 以携带缓存的请求体
                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return Flux.just(cachedDataBuffer);
                            }
                        };

                        //统计用户调用接口次数
                        return staticInterfaceInvoke(chain, finalUrl, method, uid, exchange);
                    });
        } else {
            String context = uri + "\n" + getQueryParams(exchange);
            if (!Objects.equals(sign, SignUtils.sign(context, secretKey))) {
                return handleNoAuth(response);
            }

            //统计用户调用接口次数
            return staticInterfaceInvoke(chain, url, method, uid, exchange);
        }
    }

    private Mono<Void> staticInterfaceInvoke(GatewayFilterChain chain, String path, String method, String uid, ServerWebExchange exchange) {
        Long interfaceInfoId = innerInterfaceInfoService.getInterfaceInfoId(path, method);
        Boolean addInvokeTime = innerUserInterfaceInfoService.addInvokeTime(interfaceInfoId, Long.parseLong(uid));
        if (addInvokeTime == null || !addInvokeTime) {
            log.error("统计用户接口调用次数失败！interface:{},userId:{}", interfaceInfoId, uid);
        }
        return chain.filter(exchange);
    }

    /**
     * 获取请求参数： GET 请求的查询参数并格式化为 `a=1&b=2` 的形式
     *
     * @param exchange
     * @return
     */
    private String getQueryParams(ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        return queryParams.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
                .collect(Collectors.joining("&"));
    }


    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }


    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
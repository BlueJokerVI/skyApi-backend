package com.cct.skyapigateway;

import com.cct.skyapicommon.domain.enums.RespCodeEnum;
import com.cct.skyapicommon.utils.JsonUtils;
import com.cct.skyapicommon.utils.RespUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 异常处理器
 * @author cct
 */
@Configuration
@Order(-1) // 优先级高于默认的异常处理器
@Slf4j
public class CustomExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("Global exception caught: {}", ex.getMessage(), ex);

        // 自定义错误响应
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorResponse = JsonUtils.toStr(RespUtils.error(RespCodeEnum.PARAMS_ERROR, "网关异常" + ex.getMessage()));
        byte[] bytes = errorResponse.getBytes();
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }
}

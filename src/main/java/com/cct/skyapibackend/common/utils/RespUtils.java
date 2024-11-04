package com.cct.skyapibackend.common.utils;

import com.cct.skyapibackend.common.domain.enums.RespCodeEnum;
import com.cct.skyapibackend.common.domain.vo.BaseResponse;

/**
 * @BelongsProject: skyapibackend
 * @Author: cct
 * @Description: TODO
 */
public class RespUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(0, null, "ok");
    }


    public static BaseResponse error(RespCodeEnum errorCode) {
        return new BaseResponse<>(errorCode.getCode());
    }


    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    public static BaseResponse error(RespCodeEnum errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }
}

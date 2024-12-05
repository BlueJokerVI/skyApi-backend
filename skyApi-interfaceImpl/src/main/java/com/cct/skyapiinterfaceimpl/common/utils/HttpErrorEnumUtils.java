package com.cct.skyapiinterfaceimpl.common.utils;

import cn.hutool.http.ContentType;
import com.cct.skyapicommon.utils.JsonUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: cct
 * @Description: HttpError枚举类
 */
public enum HttpErrorEnumUtils {

    /**
     *
     */
    ACCESS_DENIED(401, "认证失败");

    private final Integer code;

    private final String description;

    public void sendHttpError(HttpServletResponse response) throws IOException {
        response.setStatus(code);
        response.setCharacterEncoding("utf8");
        response.setContentType(ContentType.JSON.toString());
        response.getWriter().write(JsonUtils.toStr(ACCESS_DENIED));
    }

    HttpErrorEnumUtils(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

}

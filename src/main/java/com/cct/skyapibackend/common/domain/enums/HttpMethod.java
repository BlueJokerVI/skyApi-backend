package com.cct.skyapibackend.common.domain.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: HTTP请求类型枚举
 * @Version: 1.0
 */
@Getter
public enum HttpMethod {
    /**
     * get请求：0、管理员：1、post请求
     */
    GET(0, "GET"),
    POST(1, "POST");


    final int code;
    final String desc;

    HttpMethod(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据 code 值获取枚举实例
     *
     * @param code 枚举的 code 值
     * @return 对应的枚举实例，如果未找到则返回 null
     */
    public static UserRoleEnum getByCode(int code) {
        for (UserRoleEnum status : UserRoleEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }

    /**
     * 获取所有枚举的 code 列表
     *
     * @return 枚举的 code 列表
     */
    public static List<Integer> getAllCodes() {
        List<Integer> codes = new ArrayList<>();
        for (UserRoleEnum status : UserRoleEnum.values()) {
            codes.add(status.getCode());
        }
        return codes;
    }
}
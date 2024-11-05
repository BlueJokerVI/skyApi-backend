package com.cct.skyapibackend.interfaceinfo.domain.enums;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: 接口状态枚举
 * @Version: 1.0
 */
@Getter
public enum InterfaceStatusEnum {
    /**
     *
     */
    RUN(0, "可用"),
    STOP(1,"不可用");

    final int code;
    final String desc;

    InterfaceStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    /**
     * 根据 code 值获取枚举实例
     * @param code 枚举的 code 值
     * @return 对应的枚举实例，如果未找到则返回 null
     */
    public static InterfaceStatusEnum getByCode(int code) {
        for (InterfaceStatusEnum status : InterfaceStatusEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }

    /**
     * 获取所有枚举的 code 列表
     * @return 枚举的 code 列表
     */
    public static List<Integer> getAllCodes() {
        List<Integer> codes = new ArrayList<>();
        for (InterfaceStatusEnum status : InterfaceStatusEnum.values()) {
            codes.add(status.getCode());
        }
        return codes;
    }
}

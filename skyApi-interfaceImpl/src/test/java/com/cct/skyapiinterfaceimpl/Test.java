package com.cct.skyapiinterfaceimpl;

import com.cct.skyapicommon.utils.JsonUtils;

import static com.cct.skyapiinterfaceimpl.common.utils.HttpErrorEnumUtils.ACCESS_DENIED;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: TODO
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(JsonUtils.toStr(ACCESS_DENIED));
    }
}

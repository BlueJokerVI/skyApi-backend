package com.cct.skyapicommon.constant;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: redis相关常量
 * @Version: 1.0
 */

public interface RedisConstant {

    /**
     * 项目前缀
     */
    String PROJECT_PREFIX = "skyapi:";

    /**
     * 校验随机数集合
     */
    String NONCE_SET_PREFIX = PROJECT_PREFIX + "nonce:";

    /**
     * redis中存储了每个用户对应的ak，sk
     * 用户ak，sk的key前缀
     */
    String USER_AK_SK_PREFIX = PROJECT_PREFIX + "ak-sk:";

}
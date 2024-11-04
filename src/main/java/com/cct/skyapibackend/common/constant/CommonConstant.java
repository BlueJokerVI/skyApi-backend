package com.cct.skyapibackend.common.constant;

import cn.hutool.core.date.DateField;

import java.util.concurrent.TimeUnit;

/**
 * 通用常量
 * @author cct
 */
public interface CommonConstant {

    /**
     * 升序
     */
    String SORT_ORDER_ASC = "ascend";

    /**
     * 降序
     */
    String SORT_ORDER_DESC = " descend";

    /**
     * 登录用户session的key
     */
    String LOGIN_USER_INFO_SESSION_KEY = "loginUserKey";

    /**
     * 请求头中cookie内SESSION
     */
    String SESSION= "SESSION";

    /**
     * accessToken的过期时间，10分钟过期
     */
    DateField ACCESS_TOKEN_EXPIRE_DATEFIELD = DateField.SECOND;
    TimeUnit ACCESS_TOKEN_EXPIRE_TIMEUNIT = TimeUnit.SECONDS;
    int ACCESS_TOKEN_EXPIRE_OFFSET = 30;



    /**
     * refreshToken的过期时间，7天过期
     */
    DateField REFRESH_TOKEN_EXPIRE_DATEFIELD = DateField.DAY_OF_YEAR;
    TimeUnit REFRESH_TOKEN_EXPIRE_TIMEUNIT = TimeUnit.DAYS;
    int REFRESH_TOKEN_EXPIRE_OFFSET = 7;
}

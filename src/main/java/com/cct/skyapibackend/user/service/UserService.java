package com.cct.skyapibackend.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.skyapibackend.common.domain.enums.RespCodeEnum;
import com.cct.skyapibackend.common.utils.HttpUtils;
import com.cct.skyapibackend.common.utils.ThrowUtils;
import com.cct.skyapibackend.user.domain.dto.*;
import com.cct.skyapibackend.user.domain.entity.User;
import com.cct.skyapibackend.user.domain.vo.TokenVo;
import com.cct.skyapibackend.user.domain.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.cct.skyapibackend.common.constant.CommonConstant.LOGIN_USER_INFO_SESSION_KEY;

/**
* @author cct
* @description
* @createDate 2024-08-13 19:14:13
*/

public interface UserService{
    /**
     * 用户注册
     * @param account
     * @param password
     * @param repeatPassword
     * @return
     */
    Boolean register(String account,String password,String repeatPassword);

    /**
     * 用户登录使用Session实现用户认证
     * @param account
     * @param password
     * @return
     */
    User login(String account, String password);


    /**
     * 用户登录使用JWT双token实现用户认证
     *
     * @param userLoginRequest
     * @return
     */
    UserVo login(UserLoginRequest userLoginRequest);



    /**
     * 用户修改自身信息
     * @param customUserInfoRequest
     * @return
     */
    User customUserInfo(CustomUserInfoRequest customUserInfoRequest);


    /**
     * 获取当前登入用户信息
     * @return
     */
    UserVo getCurrentUser();


    /**
     * 禁用用户
     * @param userId
     * @return
     */
    Long banUser(Long userId);

    /**
     * 用户更新密码
     * @param updatePasswordReq
     * @return
     */
    void updatePassword(UpdatePasswordReq updatePasswordReq);

    /**
     * 管理员更新用户信息
     * @param updateUserInfoByAdminReq
     */
    void updateUserInfoByAdmin(UpdateUserInfoByAdminReq updateUserInfoByAdminReq);

    /**
     * 获取用户列表
     * @param getUserListRequest
     * @return
     */
    Page<User> getUserList(GetUserListRequest getUserListRequest);


    /**
     * 刷新token
     * @param refreshTokenRequest
     * @return
     */
    TokenVo refreshToken(RefreshTokenRequest refreshTokenRequest);

}

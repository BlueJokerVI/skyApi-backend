package com.cct.skyapibackend.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.skyapibackend.common.annotation.Login;
import com.cct.skyapibackend.common.annotation.RoleAccess;
import com.cct.skyapibackend.common.domain.enums.RespCodeEnum;
import com.cct.skyapibackend.common.domain.enums.UserRoleEnum;
import com.cct.skyapibackend.common.domain.vo.BasePageResp;
import com.cct.skyapibackend.common.domain.vo.BaseResponse;
import com.cct.skyapibackend.common.utils.RespUtils;
import com.cct.skyapibackend.common.utils.ThrowUtils;
import com.cct.skyapibackend.oss.MinIOTemplate;
import com.cct.skyapibackend.common.domain.dto.OssRequest;
import com.cct.skyapibackend.common.domain.vo.OssResponse;
import com.cct.skyapibackend.user.domain.dto.*;
import com.cct.skyapibackend.user.domain.entity.User;
import com.cct.skyapibackend.user.domain.vo.TokenVo;
import com.cct.skyapibackend.user.domain.vo.UserVo;
import com.cct.skyapibackend.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @BelongsProject: skyapibackend
 * @Author: cct
 * @Description: 用户控制器
 */

@RestController
@RequestMapping("/user")
@Api(tags = "用户控制器")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    private MinIOTemplate minioTemplate;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    BaseResponse<Void> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {

        //校验
        if (userRegisterRequest.getRepeatPassword().equals(userRegisterRequest.getPassword())) {
            RespUtils.error(RespCodeEnum.PARAMS_ERROR, "密码与校验密码不一致");
        }
        //调用service方法
        ThrowUtils.throwIf(!userService.register(userRegisterRequest.getAccount(),
                        userRegisterRequest.getPassword(),
                        userRegisterRequest.getRepeatPassword()),
                RespCodeEnum.SYSTEM_ERROR, "注册失败！");
        return RespUtils.success();
    }


    @ApiOperation("用户登录")
    @PostMapping("/login")
    BaseResponse<UserVo> login(@Valid @RequestBody UserLoginRequest userLoginRequest, HttpServletResponse httpServletResponse) {
        User user = userService.login(userLoginRequest.getAccount(), userLoginRequest.getPassword());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return RespUtils.success(userVo);
    }

    @ApiOperation("用户登录双token认证")
    @PostMapping("/loginWithToken")
    BaseResponse<UserVo> loginWithToken(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        UserVo userVo = userService.login(userLoginRequest);
        return RespUtils.success(userVo);
    }


    @ApiOperation("刷新token请求")
    @PostMapping("/refresh-token")
    BaseResponse<TokenVo> loginWithToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        TokenVo tokenVo = userService.refreshToken(refreshTokenRequest);
        return RespUtils.success(tokenVo);
    }



    @ApiOperation("自定义用户信息")
    @PostMapping("/customUserInfo")
    @Login
    BaseResponse<UserVo> customUserInfo(@Valid @RequestBody CustomUserInfoRequest customUserInfoRequest) {
        User user = userService.customUserInfo(customUserInfoRequest);
        return RespUtils.success(UserVo.getVo(user));
    }


    @ApiOperation("分页获取用户列表")
    @PostMapping("/userList")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<BasePageResp<UserVo>> getUserList(@Valid @RequestBody GetUserListRequest getUserListRequest) {
        Page<User> userList = userService.getUserList(getUserListRequest);
        BasePageResp<User> basePageResp = BasePageResp.init(userList);
        BasePageResp<UserVo> userVoBasePageResp = basePageResp.toVo(basePageResp, UserVo.class);
        return RespUtils.success(userVoBasePageResp);
    }

    @ApiOperation("获取上传图片地址")
    @PostMapping("/getOssUrl")
    @Login
    BaseResponse<OssResponse> getOssUrl(@Valid @RequestBody OssRequest ossRequest) {
        OssResponse preSignedObjectUrl = minioTemplate.getPreSignedObjectUrl(ossRequest);
        return RespUtils.success(preSignedObjectUrl);
    }


    @ApiOperation("禁用用户")
    @GetMapping("/banUser")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<Long> banUser(@Valid @RequestParam("userId") Long userId) {
        return RespUtils.success(userService.banUser(userId));
    }

    @ApiOperation("用户修改密码")
    @PostMapping("/updatePassword")
    BaseResponse<Void> updatePassword(@Valid @RequestBody UpdatePasswordReq updatePasswordReq) {
        userService.updatePassword(updatePasswordReq);
        return RespUtils.success();
    }


    @ApiOperation("管理员修改用户信息")
    @PostMapping("/updateUserInfoByAdmin")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<Void> updateUserInfoByAdmin(@Valid @RequestBody UpdateUserInfoByAdminReq updateUserInfoByAdminReq) {
        userService.updateUserInfoByAdmin(updateUserInfoByAdminReq);
        return RespUtils.success();
    }

    @ApiOperation("测试")
    @GetMapping("/test")
    @Login
    BaseResponse<String> test() {
        return RespUtils.success("test");
    }
}

package com.cct.skyapibackend.user.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.skyapibackend.common.domain.dto.RequestInfo;
import com.cct.skyapicommon.domain.dto.AKSK;
import com.cct.skyapicommon.domain.enums.RespCodeEnum;
import com.cct.skyapibackend.common.domain.enums.UserRoleEnum;
import com.cct.skyapibackend.common.utils.*;
import com.cct.skyapibackend.user.constant.UserModelConstant;
import com.cct.skyapibackend.user.dao.UserDao;
import com.cct.skyapibackend.user.domain.dto.*;
import com.cct.skyapibackend.user.domain.entity.User;
import com.cct.skyapibackend.user.domain.enums.GenderEnum;
import com.cct.skyapibackend.user.domain.vo.TokenVo;
import com.cct.skyapibackend.user.domain.vo.UserVo;
import com.cct.skyapibackend.user.service.UserService;
import com.cct.skyapicommon.utils.RedisUtils;
import com.cct.skyapicommon.utils.ThrowUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

import static com.cct.skyapibackend.common.constant.CommonConstant.*;
import static com.cct.skyapibackend.common.constant.RedisConstant.*;


/**
 * @BelongsProject: skyapibackend
 * @Author: cct
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private JwtUtils jwtUtils;


    @Override
    public Boolean register(String account, String password, String repeatPassword) {

        //1.比较密码与重复密码是否一致
        ThrowUtils.throwIf(!password.equals(repeatPassword), RespCodeEnum.PARAMS_ERROR, "两次输入密码不一致");

        //2.密码加密
        String newPassword = encryption(password);

        //3.校验账户是否已经存在
        ThrowUtils.throwIf(userDao.getByAccount(account) != null, RespCodeEnum.PARAMS_ERROR, "该账号已存在");

        //4.注册用户
        User user = User.builder().account(account).password(newPassword).build();

        //5.给用户分配ak,sk
        String ak = IdUtil.fastSimpleUUID();
        String sk = IdUtil.fastSimpleUUID();
        user.setAccessKey(ak);
        user.setSecretKey(sk);
        return userDao.save(user);
    }


    /**
     * 加密
     *
     * @param password
     * @return
     */
    private static String encryption(String password) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        return md5.digestHex(password + UserModelConstant.SALT);
    }

    @Override
    public User login(String account, String password) {

        //1.校验该账户是否存在
        User user = userDao.getByAccount(account);
        ThrowUtils.throwIf(user == null, RespCodeEnum.NOT_FOUND_ERROR, "该账号不存在");
        //2.校验用户是否被封禁
        ThrowUtils.throwIf(user.getUserRole() == UserRoleEnum.BLACK_USER.getCode(), RespCodeEnum.FORBIDDEN_ERROR, "小黑子坏的很，还想访问？");
        //3.比较密码加密后与数据库存在的密码是否相同
        String encryptionPassword = encryption(password);
        String userPassword = user.getPassword();
        ThrowUtils.throwIf(!encryptionPassword.equals(userPassword), RespCodeEnum.PARAMS_ERROR, "密码错误");
        //3.存储登入用户session信息
        // 获取当前的 HttpSession
        // 从 RequestContextHolder 获取当前请求的 RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ThrowUtils.throwIf(requestAttributes == null, RespCodeEnum.OPERATION_ERROR, "用户信息存入session失败");
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER_INFO_SESSION_KEY, UserVo.getVo(user));
        return user;
    }

    @Override
    public UserVo login(UserLoginRequest userLoginRequest) {
        //1.校验该账户是否存在
        User user = userDao.getByAccount(userLoginRequest.getAccount());
        ThrowUtils.throwIf(user == null, RespCodeEnum.NOT_FOUND_ERROR, "该账号不存在");
        //2.校验用户是否被封禁
        ThrowUtils.throwIf(user.getUserRole() == UserRoleEnum.BLACK_USER.getCode(), RespCodeEnum.FORBIDDEN_ERROR, "小黑子坏的很，还想访问？");
        //3.比较密码加密后与数据库存在的密码是否相同
        String encryptionPassword = encryption(userLoginRequest.getPassword());
        String userPassword = user.getPassword();
        ThrowUtils.throwIf(!encryptionPassword.equals(userPassword), RespCodeEnum.PARAMS_ERROR, "密码错误");

        //4.判断用户是否已经登录过，如果登录过就刷新refreshToken过期时间,没有就创建新的refreshToken
        String refreshToken = RedisUtils.get(REFRESH_TOKEN_PREFIX + user.getId(), String.class);
        if (refreshToken == null) {
            refreshToken = IdUtil.fastSimpleUUID();
        }

        //5.生成用户token相关信息
        String accessToken = jwtUtils.createToken(user.getId());
        //6.包装userVo
        UserVo userVo = UserVo.getVo(user);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setAccessToken(accessToken);
        tokenVo.setRefreshToken(refreshToken);
        Date createTime = jwtUtils.getCreateTimeOrNull(accessToken);
        DateTime expireTime = DateUtil.offset(createTime, ACCESS_TOKEN_EXPIRE_DATEFIELD, ACCESS_TOKEN_EXPIRE_OFFSET);
        tokenVo.setExpires(expireTime);
        userVo.setTokenVo(tokenVo);
        //7.将登录的用户信息、refreshToken、ak、sk存入redis
        RedisUtils.set(LOGIN_USER_KEY_PREFIX + user.getId(), userVo, REFRESH_TOKEN_EXPIRE_OFFSET, REFRESH_TOKEN_EXPIRE_TIMEUNIT);
        RedisUtils.set(REFRESH_TOKEN_PREFIX + user.getId(), refreshToken, REFRESH_TOKEN_EXPIRE_OFFSET, REFRESH_TOKEN_EXPIRE_TIMEUNIT);
        AKSK aksk = new AKSK();
        aksk.setAccessKey(user.getAccessKey());
        aksk.setSecretKey(user.getSecretKey());
        RedisUtils.set(USER_AK_SK_PREFIX + user.getId(), aksk);
        return userVo;
    }

    @Override
    public User customUserInfo(CustomUserInfoRequest customUserInfoRequest) {
        //1.获取当前用户
        UserVo currentUser = getCurrentUser();
        User user = UserVo.getUser(currentUser);
        BeanUtils.copyProperties(customUserInfoRequest, user);
        //2.校验性别信息
        if (user.getGender() != null) {
            ThrowUtils.throwIf(GenderEnum.getByCode(user.getGender()) == null, RespCodeEnum.PARAMS_ERROR);
        }
        //3.更新数据库
        boolean updated = userDao.updateById(user);
        ThrowUtils.throwIf(!updated, RespCodeEnum.OPERATION_ERROR, "更新用户信息失败");
        //4.更新session信息
        HttpServletRequest currentRequest = HttpUtils.getCurrentRequest();
        HttpSession session = currentRequest.getSession();
        session.setAttribute(LOGIN_USER_INFO_SESSION_KEY, UserVo.getVo(user));
        return user;
    }

    @Override
    public UserVo getCurrentUser() {
        RequestInfo requestInfo = RequestHolderUtils.getValue();
        UserVo userVo = RedisUtils.get(LOGIN_USER_KEY_PREFIX + requestInfo.getUid(), UserVo.class);
        ThrowUtils.throwIf(userVo == null, RespCodeEnum.NOT_LOGIN_ERROR);
        return userVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long banUser(Long userId) {
        UserVo currentUser = getCurrentUser();
        ThrowUtils.throwIf(currentUser.getUserRole() != UserRoleEnum.ADMIN_USER.getCode(), RespCodeEnum.NO_AUTH_ERROR);
        User user = userDao.getById(userId);
        ThrowUtils.throwIf(user == null, RespCodeEnum.OPERATION_ERROR, "该用户不存在!");
        ThrowUtils.throwIf(user.getId().equals(currentUser.getId()), RespCodeEnum.OPERATION_ERROR, "不要自己禁用自己哇！");
        ThrowUtils.throwIf(!userDao.banUser(userId), RespCodeEnum.OPERATION_ERROR, "拉黑失败");
        return userId;
    }

    @Override
    public void updatePassword(UpdatePasswordReq updatePasswordReq) {
        UserVo currentUser = getCurrentUser();
        //1.校验旧密码是否与新密码相同
        User user = userDao.getById(currentUser.getId());
        String newEncryptionPassword = encryption(updatePasswordReq.getNewPassword());
        ThrowUtils.throwIf(user.getPassword().equals(newEncryptionPassword), RespCodeEnum.OPERATION_ERROR, "更改密码与旧密码一致");
        //2.校验旧密码是否正确
        String oldEncryptionPassword = encryption(updatePasswordReq.getOldPassword());
        ThrowUtils.throwIf(user.getPassword().equals(oldEncryptionPassword), RespCodeEnum.OPERATION_ERROR, "密码错误");
        //3.校验两次输入的新密码是否一致
        ThrowUtils.throwIf(!updatePasswordReq.getNewPassword()
                        .equals(updatePasswordReq.getNewRepeatedPassword())
                , RespCodeEnum.PARAMS_ERROR, "两次输入的密码不一致");
        //3.更新密码
        user.setPassword(newEncryptionPassword);
        ThrowUtils.throwIf(!userDao.updateById(user), RespCodeEnum.OPERATION_ERROR, "更新密码失败");
    }

    @Override
    public void updateUserInfoByAdmin(UpdateUserInfoByAdminReq updateUserInfoByAdminReq) {
        //1.校验枚举类型是否合法
        if (updateUserInfoByAdminReq.getGender() != null) {
            ThrowUtils.throwIf(GenderEnum.getByCode(updateUserInfoByAdminReq.getGender()) == null, RespCodeEnum.PARAMS_ERROR, "没有这种性别");
        }
        //2.校验更新用户是否存在
        User user = userDao.getById(updateUserInfoByAdminReq.getId());
        //3.更新用户信息
        User newUser = updateUserInfoByAdminReq.getUser(user);
        //如果要更新密码需要加密
        if (StrUtil.isNotBlank(newUser.getPassword())) {
            newUser.setPassword(encryption(newUser.getPassword()));
        }
        ThrowUtils.throwIf(!userDao.updateById(newUser), RespCodeEnum.OPERATION_ERROR, "更新用户信息失败！");
    }

    @Override
    public Page<User> getUserList(GetUserListRequest getUserListRequest) {
        return userDao.getUserList(getUserListRequest);
    }

    @Override
    public TokenVo refreshToken(RefreshTokenRequest refreshTokenRequest) {

        //1.查看refreshToken是否存在
        String refreshToken = RedisUtils.get(REFRESH_TOKEN_PREFIX + refreshTokenRequest.getUserId(), String.class);
        ThrowUtils.throwIf(!Objects.equals(refreshToken, refreshTokenRequest.getRefreshToken()), RespCodeEnum.NOT_LOGIN_ERROR, "refreshToken过期请重新登录");


        //2.查看用户id是否存在
        User existed = userDao.getById(refreshTokenRequest.getUserId());
        ThrowUtils.throwIf(existed == null, RespCodeEnum.OPERATION_ERROR, "该用户不存在!");

        //3.重新分配token
        String accessToken = jwtUtils.createToken(refreshTokenRequest.getUserId());
        Date createTime = jwtUtils.getCreateTimeOrNull(accessToken);
        DateTime expireTime = DateUtil.offset(createTime, ACCESS_TOKEN_EXPIRE_DATEFIELD, ACCESS_TOKEN_EXPIRE_OFFSET);

        return TokenVo.builder()
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .accessToken(accessToken)
                .expires(expireTime).build();
    }
}

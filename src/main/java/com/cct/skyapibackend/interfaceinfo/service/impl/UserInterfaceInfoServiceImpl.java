package com.cct.skyapibackend.interfaceinfo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.skyapicommon.domain.enums.RespCodeEnum;
import com.cct.skyapibackend.common.domain.vo.BasePageResp;
import com.cct.skyapicommon.domain.vo.BaseResponse;
import com.cct.skyapibackend.common.utils.RespUtils;
import com.cct.skyapicommon.utils.ThrowUtils;
import com.cct.skyapibackend.interfaceinfo.dao.InterfaceInfoDao;
import com.cct.skyapibackend.interfaceinfo.dao.UserInterfaceInfoDao;
import com.cct.skyapibackend.interfaceinfo.dao.mapper.UserInterfaceInfoMapper;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.AddUserInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.SearchUserInterfaceInfoListRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.SearchUserInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.userinterfaceinfo.UpdateUserInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.entity.UserInterfaceInfo;
import com.cct.skyapibackend.interfaceinfo.domain.vo.UserInterfaceInfoVo;
import com.cct.skyapibackend.interfaceinfo.service.UserInterfaceInfoService;
import com.cct.skyapibackend.user.dao.UserDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
/**
 * @description 用户调用接口信息关系表服务层实现类
 * @author cct
 */
@Service
public class UserInterfaceInfoServiceImpl implements UserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoDao userInterfaceInfoDao;

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private UserDao userDao;

    @Resource
    private InterfaceInfoDao interfaceInfoDao;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<UserInterfaceInfoVo> addUserInterfaceInfo(AddUserInterfaceInfoRequest addUserInterfaceInfoRequest) {

        //1.校验用户是否存在
        ThrowUtils.throwIf(userDao.getById(addUserInterfaceInfoRequest.getUserId())==null,RespCodeEnum.PARAMS_ERROR,"用户不存在");
        //2.校验题目是否存在
        ThrowUtils.throwIf(interfaceInfoDao.getById(addUserInterfaceInfoRequest.getInterfaceId())==null,RespCodeEnum.PARAMS_ERROR,"接口不存在");
        //3.校验记录是否存在
        ThrowUtils.throwIf(userInterfaceInfoDao.getByUserIdAndInterfaceId(addUserInterfaceInfoRequest.getUserId(),
                addUserInterfaceInfoRequest.getInterfaceId())!=null,
                RespCodeEnum.PARAMS_ERROR,"用户调用接口记录已存在！");

        UserInterfaceInfo userInterfaceInfo = addUserInterfaceInfoRequest.toUserInterfaceInfo();
        userInterfaceInfo.setId(IdUtil.getSnowflakeNextId());
        ThrowUtils.throwIf(!userInterfaceInfoDao.save(userInterfaceInfo), RespCodeEnum.OPERATION_ERROR, "添加用户调用接口信息关系失败");
        return RespUtils.success(UserInterfaceInfoVo.toVo(userInterfaceInfo));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<Void> deleteUserInterfaceInfo(Long userInterfaceInfoId) {
        ThrowUtils.throwIf(userInterfaceInfoDao.getById(userInterfaceInfoId) == null, RespCodeEnum.OPERATION_ERROR, "用户调用接口信息关系不存在");
        ThrowUtils.throwIf(!userInterfaceInfoDao.removeById(userInterfaceInfoId), RespCodeEnum.OPERATION_ERROR, "删除用户调用接口信息关系失败");
        return RespUtils.success();
    }

    @Override
    public BaseResponse<UserInterfaceInfoVo> updateUserInterfaceInfo(UpdateUserInterfaceInfoRequest updateUserInterfaceInfoRequest) {
        UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoDao.getById(updateUserInterfaceInfoRequest);
        ThrowUtils.throwIf(oldUserInterfaceInfo == null, RespCodeEnum.OPERATION_ERROR, "用户调用接口信息关系不存在");
        BeanUtil.copyProperties(updateUserInterfaceInfoRequest, oldUserInterfaceInfo, CopyOptions.create().setIgnoreNullValue(true));
        ThrowUtils.throwIf(!userInterfaceInfoDao.updateById(oldUserInterfaceInfo), RespCodeEnum.OPERATION_ERROR, "更新用户调用接口信息关系失败");
        return RespUtils.success(UserInterfaceInfoVo.toVo(oldUserInterfaceInfo));
    }

    @Override
    public BaseResponse<UserInterfaceInfoVo> searchUserInterfaceInfo(SearchUserInterfaceInfoRequest searchUserInterfaceInfoRequest) {
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoDao.searchUserInterfaceInfo(searchUserInterfaceInfoRequest);
        return RespUtils.success(UserInterfaceInfoVo.toVo(userInterfaceInfo));
    }

    @Override
    public BaseResponse<BasePageResp<UserInterfaceInfoVo>> searchUserInterfaceInfoPage(SearchUserInterfaceInfoListRequest searchUserInterfaceInfoListRequest) {
        LambdaQueryWrapper<UserInterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.select()
               .eq(searchUserInterfaceInfoListRequest.getId() != null, UserInterfaceInfo::getId, searchUserInterfaceInfoListRequest.getId())
               .eq(searchUserInterfaceInfoListRequest.getUserId() != null, UserInterfaceInfo::getUserId, searchUserInterfaceInfoListRequest.getUserId())
               .eq(searchUserInterfaceInfoListRequest.getInterfaceId() != null, UserInterfaceInfo::getInterfaceId, searchUserInterfaceInfoListRequest.getInterfaceId())
               .eq(searchUserInterfaceInfoListRequest.getTotalNum() != null, UserInterfaceInfo::getTotalNum, searchUserInterfaceInfoListRequest.getTotalNum())
               .eq(searchUserInterfaceInfoListRequest.getRemainNum() != null, UserInterfaceInfo::getRemainNum, searchUserInterfaceInfoListRequest.getRemainNum())
               .eq(searchUserInterfaceInfoListRequest.getStatus() != null, UserInterfaceInfo::getStatus, searchUserInterfaceInfoListRequest.getStatus())
               .ge(searchUserInterfaceInfoListRequest.getUpdateTime() != null, UserInterfaceInfo::getUpdateTime, searchUserInterfaceInfoListRequest.getUpdateTime());
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoMapper.selectPage(searchUserInterfaceInfoListRequest.plusPage(), wrapper);
        BasePageResp<UserInterfaceInfo> basePageResp = BasePageResp.init(userInterfaceInfoPage);
        return RespUtils.success(basePageResp.toVo(basePageResp, UserInterfaceInfoVo.class));
    }
}

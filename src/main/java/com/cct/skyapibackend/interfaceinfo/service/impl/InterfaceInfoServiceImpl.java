package com.cct.skyapibackend.interfaceinfo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.skyapibackend.common.domain.enums.RespCodeEnum;
import com.cct.skyapibackend.common.domain.vo.BasePageResp;
import com.cct.skyapibackend.common.domain.vo.BaseResponse;
import com.cct.skyapibackend.common.utils.RespUtils;
import com.cct.skyapibackend.common.utils.ThrowUtils;
import com.cct.skyapibackend.interfaceinfo.dao.InterfaceInfoDao;
import com.cct.skyapibackend.interfaceinfo.dao.mapper.InterfaceInfoMapper;
import com.cct.skyapibackend.interfaceinfo.domain.dto.AddInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.SearchInterfaceInfoListRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.SearchInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.dto.UpdateInterfaceInfoRequest;
import com.cct.skyapibackend.interfaceinfo.domain.entity.InterfaceInfo;
import com.cct.skyapibackend.interfaceinfo.domain.enums.InterfaceStatusEnum;
import com.cct.skyapibackend.interfaceinfo.domain.vo.InterfaceInfoVo;
import com.cct.skyapibackend.interfaceinfo.service.InterfaceInfoService;
import com.cct.skyapibackend.user.dao.UserDao;
import com.cct.skyapibackend.user.domain.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author cct
 * @description 接口信息表服务层实现类
 */
@Service
public class InterfaceInfoServiceImpl implements InterfaceInfoService {

    @Resource
    private UserDao userDao;

    @Resource
    private InterfaceInfoDao interfaceInfoDao;

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<InterfaceInfoVo> addInterfaceInfo(AddInterfaceInfoRequest addInterfaceInfoRequest) {
        InterfaceInfo interfaceInfo = addInterfaceInfoRequest.toInterfaceInfo();
        interfaceInfo.setId(IdUtil.getSnowflakeNextId());

        //1.校验接口创建者id是否合法
        Long createUserId = addInterfaceInfoRequest.getUserId();
        User existed = userDao.getById(createUserId);
        ThrowUtils.throwIf(existed == null, RespCodeEnum.PARAMS_ERROR, "接口创建者不存在");

        //2.保存接口信息
        interfaceInfo.setStatus(InterfaceStatusEnum.RUN.getCode());
        ThrowUtils.throwIf(!interfaceInfoDao.save(interfaceInfo), RespCodeEnum.OPERATION_ERROR, "添加接口信息失败");
        return RespUtils.success(InterfaceInfoVo.toVo(interfaceInfo));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<Void> deleteInterfaceInfo(Long interfaceInfoId) {
        ThrowUtils.throwIf(interfaceInfoDao.getById(interfaceInfoId) == null, RespCodeEnum.OPERATION_ERROR, "接口信息不存在");
        ThrowUtils.throwIf(!interfaceInfoDao.removeById(interfaceInfoId), RespCodeEnum.OPERATION_ERROR, "删除接口信息失败");
        return RespUtils.success();
    }

    @Override
    public BaseResponse<InterfaceInfoVo> updateInterfaceInfo(UpdateInterfaceInfoRequest updateInterfaceInfoRequest) {
        InterfaceInfo oldInterfaceInfo = interfaceInfoDao.getById(updateInterfaceInfoRequest);
        ThrowUtils.throwIf(oldInterfaceInfo == null, RespCodeEnum.OPERATION_ERROR, "接口信息记录不存在");
        BeanUtil.copyProperties(updateInterfaceInfoRequest, oldInterfaceInfo, CopyOptions.create().setIgnoreNullValue(true));
        ThrowUtils.throwIf(!interfaceInfoDao.updateById(oldInterfaceInfo), RespCodeEnum.OPERATION_ERROR, "更新接口信息失败");
        return RespUtils.success(InterfaceInfoVo.toVo(oldInterfaceInfo));
    }

    @Override
    public BaseResponse<InterfaceInfoVo> searchInterfaceInfo(SearchInterfaceInfoRequest searchInterfaceInfoRequest) {
        InterfaceInfo interfaceInfo = interfaceInfoDao.searchInterfaceInfo(searchInterfaceInfoRequest);
        return RespUtils.success(InterfaceInfoVo.toVo(interfaceInfo));
    }

    @Override
    public BaseResponse<BasePageResp<InterfaceInfoVo>> searchInterfaceInfoPage(SearchInterfaceInfoListRequest searchInterfaceInfoListRequest) {
        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.select()
                .eq(searchInterfaceInfoListRequest.getId() != null, InterfaceInfo::getId, searchInterfaceInfoListRequest.getId())
                .eq(searchInterfaceInfoListRequest.getName() != null, InterfaceInfo::getName, searchInterfaceInfoListRequest.getName())
                .like(searchInterfaceInfoListRequest.getDescription() != null, InterfaceInfo::getDescription, searchInterfaceInfoListRequest.getDescription())
                .eq(searchInterfaceInfoListRequest.getUrl() != null, InterfaceInfo::getUrl, searchInterfaceInfoListRequest.getUrl())
                .eq(searchInterfaceInfoListRequest.getRequestHeader() != null, InterfaceInfo::getRequestHeader, searchInterfaceInfoListRequest.getRequestHeader())
                .eq(searchInterfaceInfoListRequest.getResponseHeader() != null, InterfaceInfo::getResponseHeader, searchInterfaceInfoListRequest.getResponseHeader())
                .eq(searchInterfaceInfoListRequest.getStatus() != null, InterfaceInfo::getStatus, searchInterfaceInfoListRequest.getStatus())
                .eq(searchInterfaceInfoListRequest.getMethod() != null, InterfaceInfo::getMethod, searchInterfaceInfoListRequest.getMethod())
                .eq(searchInterfaceInfoListRequest.getUserId() != null, InterfaceInfo::getUserId, searchInterfaceInfoListRequest.getUserId())
                .ge(searchInterfaceInfoListRequest.getUpdateTime() != null, InterfaceInfo::getUpdateTime, searchInterfaceInfoListRequest.getUpdateTime());
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoMapper.selectPage(searchInterfaceInfoListRequest.plusPage(), wrapper);
        BasePageResp<InterfaceInfo> basePageResp = BasePageResp.init(interfaceInfoPage);
        return RespUtils.success(basePageResp.toVo(basePageResp, InterfaceInfoVo.class));
    }
}

package com.cct.skyapibackend.interfaceinfo.service.inner;

import cn.hutool.core.util.IdUtil;
import com.cct.skyapibackend.common.annotation.RedissonLock;
import com.cct.skyapibackend.interfaceinfo.dao.UserInterfaceInfoDao;
import com.cct.skyapibackend.interfaceinfo.domain.entity.UserInterfaceInfo;
import com.cct.skyapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: 内部 InnerUserInterfaceInfoService实现类
 */


@DubboService
@Component
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoDao userInterfaceInfoDao;


    //TODO 了解SpringELUtils 表达式解析
    @Override
    @RedissonLock(key = "#interfaceId+'_'+#userId")
    public Boolean addInvokeTime(Long interfaceId, Long userId) {

        //1.查看记录是否存在
        UserInterfaceInfo existed = userInterfaceInfoDao.getByUserIdAndInterfaceId(userId, interfaceId);

        //2.不存在就创建用户调用接口记录，并设置调用次数为1，存在就调用次数加1，使用分布式锁
        if (existed == null) {
            existed = new UserInterfaceInfo();
            existed.setId(IdUtil.getSnowflakeNextId());
            existed.setInterfaceId(interfaceId);
            existed.setUserId(userId);
            //默认设置接口可调用次数1000L
            existed.setTotalNum(1L);
            existed.setRemainNum(999L);
            return userInterfaceInfoDao.save(existed);
        } else {
            existed.setTotalNum(existed.getTotalNum() + 1);
            existed.setRemainNum(existed.getRemainNum() - 1);
            return userInterfaceInfoDao.updateById(existed);
        }
    }
}

package com.cct.skyapibackend.interfaceinfo.service.inner;

import com.cct.skyapibackend.common.annotation.RedissonLock;
import com.cct.skyapibackend.interfaceinfo.dao.InterfaceInfoDao;
import com.cct.skyapicommon.service.InnerInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @BelongsProject: skyApi-backend
 * @Author: cct
 * @Description: 内部  InnerInterfaceInfoService实现类
 */
@DubboService
@Component
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoDao interfaceInfoDao;
    @Override
    public Long getInterfaceInfoId(String url, String method) {
        return interfaceInfoDao.getIdByUrlAndMethod(url,method);
    }
}

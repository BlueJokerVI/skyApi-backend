package com.cct.skyapiinterfaceimpl.ipSearch.service.impl;

import cn.hutool.core.io.IoUtil;
import com.cct.skyapicommon.domain.enums.RespCodeEnum;
import com.cct.skyapicommon.exception.BusinessException;
import com.cct.skyapiinterfaceimpl.ipSearch.model.IPInfo;
import com.cct.skyapiinterfaceimpl.ipSearch.service.IPService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author cct
 * ip服务实现类
 */

@Component
@Slf4j
public class IPServiceImpl implements IPService {


    private static final String XDB_PATH = "ipdata/ip2region.xdb";
    private Searcher searcher;

    IPServiceImpl() {
        ClassPathResource resource = new ClassPathResource(XDB_PATH);

        byte[] cBuff = new byte[0];
        // 通过 InputStream 读取资源文件
        try (InputStream inputStream = resource.getInputStream()) {
            cBuff = IoUtil.readBytes(inputStream);
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            log.error("IPServiceImpl failed to load content or create searcher: %s\n", e);
        }

        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            log.error("IPServiceImpl failed to create content cached searcher: %s\n", e);
            return;
        }
    }

    @Override
    public IPInfo searchIP(String ip) {
        IPInfo ipInfo = new IPInfo();
        try {
            //search格式如下 中国|0|浙江省|绍兴市|移动
            String search = searcher.search(ip);
            String[] split = search.split("\\|");
            ipInfo.setCountry(split[0]);
            ipInfo.setNet(split[4]);
            ipInfo.setIp(ip);
            StringBuilder area = new StringBuilder();
            for (int i = 1; i <= 3; i++) {
                if (!Objects.equals(split[i], "0")) {
                    area.append(split[i]);
                    area.append(" ");
                }
            }
            ipInfo.setArea(area.toString());

        } catch (Exception e) {
            throw new BusinessException(RespCodeEnum.OPERATION_ERROR, "ip 解析失败");
        }
        return ipInfo;
    }
}

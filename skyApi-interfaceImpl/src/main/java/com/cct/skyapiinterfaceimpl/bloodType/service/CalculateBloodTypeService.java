package com.cct.skyapiinterfaceimpl.bloodType.service;

import com.cct.skyapiinterfaceimpl.bloodType.model.BloodTypePossible;

/**
 * @author cct
 * 计算血型服务
 */
public interface CalculateBloodTypeService {

    /**
     * 计算肯的血型
     * @param fatherBloodType
     * @param motherBloodType
     * @return
     */
    BloodTypePossible getMaybeBloodType(String fatherBloodType,String motherBloodType);
}

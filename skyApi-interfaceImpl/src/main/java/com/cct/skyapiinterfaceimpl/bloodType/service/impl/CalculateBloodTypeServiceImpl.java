package com.cct.skyapiinterfaceimpl.bloodType.service.impl;

import com.cct.skyapicommon.domain.enums.RespCodeEnum;
import com.cct.skyapicommon.exception.BusinessException;
import com.cct.skyapiinterfaceimpl.bloodType.model.BloodTypePossible;
import com.cct.skyapiinterfaceimpl.bloodType.model.enums.BloodTypeEnum;
import com.cct.skyapiinterfaceimpl.bloodType.service.CalculateBloodTypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 计算血型
 *
 * @author cct
 */
@Service
public class CalculateBloodTypeServiceImpl implements CalculateBloodTypeService {

    @Override
    public BloodTypePossible getMaybeBloodType(String fatherBloodType, String motherBloodType) {

        // 校验父母血型是否合法
        if (!isValidBloodType(fatherBloodType)) {
            throw new BusinessException(RespCodeEnum.PARAMS_ERROR, "父亲的血型无效: " + fatherBloodType);
        }
        if (!isValidBloodType(motherBloodType)) {
            throw new BusinessException(RespCodeEnum.PARAMS_ERROR, "母亲的血型无效: " + motherBloodType);
        }


        // 初始化可能的血型和不可能的血型
        List<String> possible = new ArrayList<>();
        List<String> impossible = new ArrayList<>();

        // 解析父母的血型
        String fatherABO = BloodTypeEnum.getABO(fatherBloodType);
        String motherABO = BloodTypeEnum.getABO(motherBloodType);


        // 根据 ABO 血型推算
        switch (fatherABO) {
            case "A":
                switch (motherABO) {
                    case "A":
                        possible.addAll(Arrays.asList("A", "O"));
                        impossible.addAll(Arrays.asList("B", "AB"));
                        break;
                    case "B":
                        possible.addAll(Arrays.asList("A", "B", "AB", "O"));
                        break;
                    case "AB":
                        possible.addAll(Arrays.asList("A", "B", "AB"));
                        impossible.add("O");
                        break;
                    case "O":
                        possible.addAll(Arrays.asList("A", "O"));
                        impossible.addAll(Arrays.asList("B", "AB"));
                        break;
                }
                break;
            case "B":
                switch (motherABO) {
                    case "A":
                        possible.addAll(Arrays.asList("A", "B", "AB", "O"));
                        break;
                    case "B":
                        possible.addAll(Arrays.asList("B", "O"));
                        impossible.addAll(Arrays.asList("A", "AB"));
                        break;
                    case "AB":
                        possible.addAll(Arrays.asList("A", "B", "AB"));
                        impossible.add("O");
                        break;
                    case "O":
                        possible.addAll(Arrays.asList("B", "O"));
                        impossible.addAll(Arrays.asList("A", "AB"));
                        break;
                }
                break;
            case "AB":
                switch (motherABO) {
                    case "A":
                        possible.addAll(Arrays.asList("A", "B", "AB"));
                        impossible.add("O");
                        break;
                    case "B":
                        possible.addAll(Arrays.asList("A", "B", "AB"));
                        impossible.add("O");
                        break;
                    case "AB":
                        possible.addAll(Arrays.asList("A", "B", "AB"));
                        impossible.add("O");
                        break;
                    case "O":
                        possible.addAll(Arrays.asList("A", "B"));
                        impossible.addAll(Arrays.asList("O", "AB"));
                        break;
                }
                break;
            case "O":
                switch (motherABO) {
                    case "A":
                        possible.addAll(Arrays.asList("A", "O"));
                        impossible.addAll(Arrays.asList("B", "AB"));
                        break;
                    case "B":
                        possible.addAll(Arrays.asList("B", "O"));
                        impossible.addAll(Arrays.asList("A", "AB"));
                        break;
                    case "AB":
                        possible.addAll(Arrays.asList("A", "B"));
                        impossible.addAll(Arrays.asList("O", "AB"));
                        break;
                    case "O":
                        possible.addAll(Arrays.asList("O"));
                        impossible.addAll(Arrays.asList("A", "B", "AB"));
                        break;
                }
                break;
        }

        // 根据 Rh 因子推算
        String faRH = BloodTypeEnum.getRh(fatherBloodType);
        String moRH = BloodTypeEnum.getRh(motherBloodType);

        if (faRH == null || moRH == null) {
            possible.add("RH_POSITIVE");
            possible.add("RH_NEGATIVE");
        } else {
            boolean fatherRhPositive = BloodTypeEnum.isRhPositive(fatherBloodType);
            boolean motherRhPositive = BloodTypeEnum.isRhPositive(motherBloodType);
            if (fatherRhPositive && motherRhPositive) {
                possible.add("RH_POSITIVE");
                impossible.add("RH_NEGATIVE");
            } else if (!fatherRhPositive && !motherRhPositive) {
                possible.add("RH_NEGATIVE");
                impossible.add("RH_POSITIVE");
            } else {
                possible.add("RH_POSITIVE");
                possible.add("RH_NEGATIVE");
            }
        }

        // 返回结果
        return new BloodTypePossible(possible, impossible);
    }


    /**
     * 校验血型是否合法（忽略大小写，并支持 A+ 和 A- 格式）
     *
     * @param bloodType
     * @return
     */
    private boolean isValidBloodType(String bloodType) {
        if (bloodType == null || bloodType.trim().isEmpty()) {
            return false;
        }

        // 转换为统一大写
        String normalizedBloodType = bloodType.toUpperCase();

        // 获取 ABO 血型部分和 Rh 因子部分
        String abo = BloodTypeEnum.getABO(normalizedBloodType);
        String rh = BloodTypeEnum.getRh(normalizedBloodType);

        // 校验 ABO 血型是否有效
        if (!BloodTypeEnum.isValidABO(abo)) {
            return false;
        }

        // 如果有 Rh 因子部分，校验其是否有效
        if (rh != null && !BloodTypeEnum.isValidRh(rh)) {
            return false;
        }

        return true;
    }
}

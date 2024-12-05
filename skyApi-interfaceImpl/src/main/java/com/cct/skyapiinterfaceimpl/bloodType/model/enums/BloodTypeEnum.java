package com.cct.skyapiinterfaceimpl.bloodType.model.enums;

import java.util.Objects;

/**
 * @author cct
 * 血型枚举
 */
public enum BloodTypeEnum {
    /**/
    A, B, AB, O, RH_POSITIVE, RH_NEGATIVE;

    /**
     * 校验 ABO 血型（忽略大小写）
     *
     * @param bloodType
     * @return
     */
    public static boolean isValidABO(String bloodType) {
        String bloodTypeUpper = bloodType.toUpperCase();
        return "A".equals(bloodTypeUpper) || "B".equals(bloodTypeUpper) || "AB".equals(bloodTypeUpper) || "O".equals(bloodTypeUpper);
    }

    /**
     * 校验 Rh 因子（忽略大小写）
     *
     * @param rh
     * @return
     */
    public static boolean isValidRh(String rh) {
        String rhUpper = rh.toUpperCase();
        return "RH_POSITIVE".equals(rhUpper) || "RH_NEGATIVE".equals(rhUpper);
    }

    /**
     * 获取 ABO 血型部分（忽略大小写）
     *
     * @param bloodType
     * @return
     */
    public static String getABO(String bloodType) {
        if (bloodType.contains("+") || bloodType.contains("-")) {
            String abo = bloodType.substring(0, bloodType.length() - 1).toUpperCase();
            // 处理 A+ / A- 格式
            return abo;
        } else if (bloodType.contains("RH")) {
            // 提取 ABO 血型部分
            return bloodType.split(" ")[0].toUpperCase();
        }
        // 如果没有 RH 部分，则直接返回 ABO 血型
        return bloodType.toUpperCase();
    }

    /**
     * 获取 Rh 因子部分（忽略大小写）
     *
     * @param bloodType
     * @return
     */
    public static String getRh(String bloodType) {
        if (bloodType.contains("+") || bloodType.contains("-")) {
            // 处理 A+ / A- 格式
            return bloodType.endsWith("+") ? "RH_POSITIVE" : "RH_NEGATIVE";
        } else if (bloodType.contains("RH")) {
            // 提取 Rh 因子部分
            return bloodType.split(" ")[1].toUpperCase();
        }
        // 如果没有 Rh 因子，返回 null
        return null;
    }

    /**
     * 判断 Rh 因子是否为正
     *
     * @param bloodType
     * @return
     */
    public static Boolean isRhPositive(String bloodType) {
        return Objects.equals(getRh(bloodType), "RH_POSITIVE");
    }

    /**
     * 判断 Rh 因子是否为负
     *
     * @param bloodType
     * @return
     */
    public static Boolean isRhNegative(String bloodType) {
        return Objects.equals(getRh(bloodType), "RH_NEGATIVE");
    }
}



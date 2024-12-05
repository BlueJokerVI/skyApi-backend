package com.cct.skyapiinterfaceimpl.bloodType.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author cct
 * 可能血型包装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodTypePossible {

    List<String> possible;

    List<String> impossible;
}
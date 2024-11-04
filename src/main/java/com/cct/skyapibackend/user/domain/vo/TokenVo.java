package com.cct.skyapibackend.user.domain.vo;

import com.cct.skyapibackend.user.domain.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: skyapibackend
 * @Author: cct
 * @Description: 用户登入后返回用户Vo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenVo implements Serializable {

    private static final long serialVersionUID = 1369922972471506342L;

    @ApiModelProperty("accessToken")
    private String accessToken;

    @ApiModelProperty("refreshToken")
    private String refreshToken;

    @ApiModelProperty("accessToken过期时间")
    private Date expires;

}

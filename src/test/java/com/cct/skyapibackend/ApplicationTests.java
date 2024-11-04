package com.cct.skyapibackend;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.auth0.jwt.interfaces.Claim;
import com.cct.skyapibackend.common.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@SpringBootTest
class ApplicationTests {

    @Resource
    JwtUtils jwtUtils;


    public static void main(String[] args) throws InterruptedException {
        DateTime date = DateUtil.date();
        DateTime offset = DateUtil.offset(date, DateField.DAY_OF_YEAR, 100);
        System.out.println(offset);

    }

    @Test
    void test(){
        String token = jwtUtils.createToken(21212L);
        String tokenOld = jwtUtils.createToken(21212L);
        Map<String, Claim> stringClaimMap = jwtUtils.verifyToken(token);
        stringClaimMap.forEach(
                (k,v)->{
                    System.out.println(k+" "+v);
                }
        );
        System.out.println(token);
        System.out.println(tokenOld);
//        Boolean expire = jwtUtils.isExpire("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjIxMjEyLCJjcmVhdGVUaW1lIjoxNzMwNzAyOTIzfQ.Qfxps3w6lrh7Z7TRMO5WiF6pdQAPXnvYN8LwX-yoFe4"
//                , DateField.SECOND, 10);
//        System.out.println(expire);
    }

}

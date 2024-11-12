package com.cct.skyapibackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cct
 */
@SpringBootApplication(scanBasePackages = "com.cct")
@MapperScan("com.cct.skyapibackend.**.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

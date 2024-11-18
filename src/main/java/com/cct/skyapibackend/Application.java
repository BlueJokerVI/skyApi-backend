package com.cct.skyapibackend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cct
 */
@SpringBootApplication(scanBasePackages = "com.cct")
@MapperScan("com.cct.skyapibackend.**.mapper")
@EnableDubbo
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

package com.cct.skyapiinterfaceimpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication(scanBasePackages = "com.cct")
public class SkyApiInterfaceImplApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkyApiInterfaceImplApplication.class, args);
    }

}

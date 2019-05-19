package com.cody.codystage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableAsync
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableConfigurationProperties
@MapperScan(basePackages = {"com.cody.codystage.mapper"})

public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}

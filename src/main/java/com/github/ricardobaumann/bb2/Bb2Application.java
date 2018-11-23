package com.github.ricardobaumann.bb2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableRetry
@EnableFeignClients
@SpringBootApplication
public class Bb2Application {

    public static void main(String[] args) {
        SpringApplication.run(Bb2Application.class, args);
    }
}

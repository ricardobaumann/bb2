package com.github.ricardobaumann.bb2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
public class Config implements DisposableBean {

    @Bean
    ExecutorService executorService() {
        log.info("Creating executor service");
        return Executors.newFixedThreadPool(100);
    }

    @Override
    public void destroy() {
        log.info("Shutting down executor service");
        executorService().shutdown();
    }
}

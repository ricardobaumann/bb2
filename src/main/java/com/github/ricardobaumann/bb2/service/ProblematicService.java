package com.github.ricardobaumann.bb2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProblematicService {

    @Retryable(value = RuntimeException.class, maxAttempts = 10, backoff = @Backoff(delay = 5000))
    public void testProblematicFeature() {
        log.info("I am running");
        throw new RuntimeException("dohhh");
    }

    @Recover
    void recover(RuntimeException e) {
        log.error("handling exception", e);
    }
}

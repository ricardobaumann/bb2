package com.github.ricardobaumann.bb2;

import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
class DummyRepoConfig {


    @Bean
    ErrorDecoder errorDecoder() {
        return new ErrorDecoder.Default() {
            @Override
            public Exception decode(String s, Response response) {
                if (409 == response.status()) {
                    log.info("Handling 409");
                    return new RetryableException("getting conflict and retry", null);
                } else {
                    return super.decode(s, response);
                }
            }
        };
    }

    @Bean
    Retryer retryer() {
        return new Retryer.Default(1, 100, 2);
    }
}

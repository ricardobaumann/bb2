package com.github.ricardobaumann.bb2.config;

import com.github.ricardobaumann.bb2.model.FeatureSetting;
import com.github.ricardobaumann.bb2.repo.FeatureSettingRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Configuration
public class Init {

    private final FeatureSettingRepo featureSettingRepo;

    public Init(FeatureSettingRepo featureSettingRepo) {
        this.featureSettingRepo = featureSettingRepo;
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> init();
    }

    private void init() {
        AtomicLong id = new AtomicLong(0);
        Stream.generate(() -> FeatureSetting.builder()
                .amount(10)
                .customerId(id.incrementAndGet())
                .processedAt(Date.from(LocalDateTime.now().minusDays(2)
                        .toInstant(ZoneOffset.UTC)))
                .feature(FeatureSetting.Feature.EYE_CATCHER)
                .build())
                .limit(10)
                .forEach(featureSettingRepo::save);
    }

}

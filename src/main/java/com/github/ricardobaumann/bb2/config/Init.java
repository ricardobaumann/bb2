package com.github.ricardobaumann.bb2.config;

import com.github.ricardobaumann.bb2.model.UserFeature;
import com.github.ricardobaumann.bb2.model.UserSettings;
import com.github.ricardobaumann.bb2.repo.FeatureSettingRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
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
        Stream.generate(() -> UserSettings.builder()
                .customerId(1232L)
                .processedAt(Date.from(LocalDateTime.now().minusDays(2)
                        .toInstant(ZoneOffset.UTC)))
                .userFeatures(Arrays.asList(UserFeature.builder()
                                .adId(1L)
                                .feature(UserFeature.Feature.eyeCatcher)
                                .build(),
                        UserFeature.builder()
                                .adId(2L)
                                .feature(UserFeature.Feature.pageOneAd)
                                .build()))
                .build())
                .limit(1)
                .forEach(this::save);
    }

    private void save(UserSettings userSettings) {
        userSettings.getUserFeatures().forEach(userFeature -> userFeature.setUserSettings(userSettings));
        featureSettingRepo.save(userSettings);
    }

}

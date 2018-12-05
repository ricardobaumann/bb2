package com.github.ricardobaumann.bb2.service;

import com.github.ricardobaumann.bb2.model.UserFeature;
import com.github.ricardobaumann.bb2.model.UserSettings;
import com.github.ricardobaumann.bb2.repo.FeatureSettingRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

@Slf4j
@Service
public class BookingService {

    private FeatureSettingRepo featureSettingRepo;
    private LimitsService limitsService;

    public BookingService(FeatureSettingRepo featureSettingRepo,
                          LimitsService limitsService,
                          ExecutorService executorService) {
        this.featureSettingRepo = featureSettingRepo;
        this.limitsService = limitsService;
    }

    public void process(UserSettings userSettings) {
        log.info("processing {}", userSettings);

        Map<UserFeature.Feature, Integer> limits = limitsService.getLimits(userSettings.getCustomerId());

        Map<UserFeature.Feature, Set<Long>> adsByFeature = new HashMap<>();

        userSettings.getUserFeatures().forEach(userFeature -> adsByFeature
                .computeIfAbsent(userFeature.getFeature(), feature -> new HashSet<>())
                .add(userFeature.getAdId()));

        Stream.of(UserFeature.Feature.values())
                .forEach(feature -> {
                    processFeature(feature, limits.get(feature), adsByFeature.get(feature));
                });


        userSettings.setProcessedAt(new Date());
        featureSettingRepo.save(userSettings);
    }

    private void processFeature(UserFeature.Feature feature, Integer limit, Set<Long> ads) {

    }
}

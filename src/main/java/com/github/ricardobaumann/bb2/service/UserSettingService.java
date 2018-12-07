package com.github.ricardobaumann.bb2.service;

import com.github.ricardobaumann.bb2.dto.Ad;
import com.github.ricardobaumann.bb2.dto.FeatureChange;
import com.github.ricardobaumann.bb2.dto.FeatureResult;
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
public class UserSettingService {

    private final BookingService bookingService;
    private final ExecutorService executorService;
    private final FeatureSettingRepo featureSettingRepo;
    private final LimitsService limitsService;
    private final InventoryService inventoryService;

    public UserSettingService(FeatureSettingRepo featureSettingRepo,
                              LimitsService limitsService,
                              InventoryService inventoryService,
                              BookingService bookingService,
                              ExecutorService executorService) {
        this.featureSettingRepo = featureSettingRepo;
        this.limitsService = limitsService;
        this.inventoryService = inventoryService;
        this.bookingService = bookingService;
        this.executorService = executorService;
    }

    public void process(UserSettings userSettings) {
        log.info("processing {}", userSettings);

        Map<UserFeature.Feature, Integer> limits = limitsService.getLimits(userSettings.getCustomerId());

        List<Ad> inventory = inventoryService.getInventory(userSettings.getCustomerId());//TODO max results

        Map<UserFeature.Feature, Set<Long>> adsByFeature = new HashMap<>();
        userSettings.getUserFeatures().forEach(userFeature -> adsByFeature
                .computeIfAbsent(userFeature.getFeature(), feature -> new HashSet<>())
                .add(userFeature.getAdId()));

        Stream.of(UserFeature.Feature.values())
                .map(feature -> processFeature(
                        userSettings.getCustomerId(),
                        inventory,
                        feature,
                        limits.getOrDefault(feature, 0),
                        adsByFeature.getOrDefault(feature, Collections.emptySet())))
                .forEach(featureResult -> updateUserSettings(userSettings, featureResult));
    }

    private void updateUserSettings(UserSettings userSettings,
                                    FeatureResult featureResult) {

        userSettings.getUserFeatures()//removing unbooked ads
                .removeIf(userFeature -> userFeature.getFeature() == featureResult.getFeature()
                        && featureResult.getAdsUnbooked().contains(userFeature.getAdId()));

        featureResult.getAdsBooked().forEach(adId -> userSettings.getUserFeatures()//adding booked ads
                .add(UserFeature.builder()
                        .feature(featureResult.getFeature())
                        .adId(adId)
                        .userSettings(userSettings)
                        .build()));

        userSettings.setProcessedAt(new Date());
        featureSettingRepo.save(userSettings);
    }

    private FeatureResult processFeature(Long customerId, List<Ad> inventory,
                                         UserFeature.Feature feature,
                                         Integer limit,
                                         Set<Long> currentAds) {

        return bookingService.applyFeatureChange(
                FeatureChange.builder()
                        .currentAds(currentAds)
                        .customerId(customerId)
                        .feature(feature)
                        .inventory(inventory)
                        .limit(limit)
                        .build());


    }
}

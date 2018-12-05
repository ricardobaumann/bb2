package com.github.ricardobaumann.bb2;

import com.github.ricardobaumann.bb2.model.UserSettings;
import com.github.ricardobaumann.bb2.repo.BillingRepo;
import com.github.ricardobaumann.bb2.repo.DummyRepo;
import com.github.ricardobaumann.bb2.repo.FeatureSettingRepo;
import com.github.ricardobaumann.bb2.service.BookingService;
import com.github.ricardobaumann.bb2.service.ProblematicService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Log4j2
@Component
public class Scheduler {

    private final FeatureSettingRepo featureSettingRepo;
    private final ExecutorService executorService;
    private final ProblematicService problematicService;
    private final DummyRepo dummyRepo;
    private BillingRepo billingRepo;
    private BookingService bookingService;

    public Scheduler(FeatureSettingRepo featureSettingRepo,
                     ExecutorService executorService,
                     ProblematicService problematicService,
                     DummyRepo dummyRepo, BillingRepo billingRepo, BookingService bookingService) {
        this.featureSettingRepo = featureSettingRepo;
        this.executorService = executorService;
        this.problematicService = problematicService;
        this.dummyRepo = dummyRepo;
        this.billingRepo = billingRepo;
        this.bookingService = bookingService;
    }

    @Scheduled(fixedDelay = Integer.MAX_VALUE, initialDelay = 5000)
    void processFeatureSettings() {
        Date now = new Date();

        List<UserSettings> userSettings;
        while (!CollectionUtils.isEmpty(
                userSettings = featureSettingRepo.findByProcessedAtBeforeOrderByCustomerId(
                        now,
                        PageRequest.of(0, 10))
                        .getContent())) {
            processFeatureSettingChunk(userSettings);
        }

        //dummyRepo.get().ifPresent(log::info);

        log.info("Finished**********************************");
    }

    private void processFeatureSettingChunk(List<UserSettings> userSettings) {
        log.info("Processing chunk of {} items", userSettings.size());
        CompletableFuture.allOf(
                userSettings
                        .stream()
                        .map(featureSetting -> CompletableFuture.runAsync(() -> processFeatureSetting(
                                featureSetting),
                                executorService))
                        .toArray(CompletableFuture[]::new)
        ).join();
    }

    private void processFeatureSetting(UserSettings userSettings) {
        bookingService.process(userSettings);
    }

}

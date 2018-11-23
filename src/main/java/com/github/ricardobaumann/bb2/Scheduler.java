package com.github.ricardobaumann.bb2;

import com.github.ricardobaumann.bb2.model.FeatureSetting;
import com.github.ricardobaumann.bb2.repo.FeatureSettingRepo;
import com.github.ricardobaumann.bb2.service.ProblematicService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Log4j2
@Component
public class Scheduler {

    private final FeatureSettingRepo featureSettingRepo;
    private final ExecutorService executorService;
    private ProblematicService problematicService;
    private DummyRepo dummyRepo;

    public Scheduler(FeatureSettingRepo featureSettingRepo,
                     ExecutorService executorService,
                     ProblematicService problematicService,
                     DummyRepo dummyRepo) {
        this.featureSettingRepo = featureSettingRepo;
        this.executorService = executorService;
        this.problematicService = problematicService;
        this.dummyRepo = dummyRepo;
    }

    @Scheduled(fixedDelay = Integer.MAX_VALUE, initialDelay = 5000)
    void processFeatureSettings() {
        /*Date now = new Date();

        List<FeatureSetting> featureSettings;
        while (!CollectionUtils.isEmpty(
                featureSettings = featureSettingRepo.findByProcessedAtBeforeOrderByCustomerId(
                        now,
                        PageRequest.of(0, 2))
                        .getContent())) {
            processFeatureSettingChunk(featureSettings);
        }*/

        //problematicService.testProblematicFeature();

        log.info(dummyRepo.get().getName());

        log.info("Finished**********************************");
    }

    private void processFeatureSettingChunk(List<FeatureSetting> featureSettings) {
        log.info("Processing chunk of {} items", featureSettings.size());
        CompletableFuture.allOf(
                featureSettings
                        .stream()
                        .map(featureSetting -> CompletableFuture.runAsync(() -> processFeatureSetting(
                                featureSetting),
                                executorService))
                        .toArray(CompletableFuture[]::new)
        ).join();
    }

    private void processFeatureSetting(FeatureSetting featureSetting) {
        log.info("processing {}", featureSetting);
        featureSetting.setProcessedAt(new Date());
        featureSettingRepo.save(featureSetting);
        try {
            Thread.sleep(1000);//Slooooow
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

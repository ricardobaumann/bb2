package com.github.ricardobaumann.bb2.service;

import com.github.ricardobaumann.bb2.dto.Limits;
import com.github.ricardobaumann.bb2.model.UserFeature;
import com.github.ricardobaumann.bb2.repo.BillingRepo;
import com.github.ricardobaumann.bb2.repo.LimitsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LimitsService {

    private final BillingRepo billingRepo;

    public LimitsService(BillingRepo billingRepo) {
        this.billingRepo = billingRepo;
    }

    Map<UserFeature.Feature, Integer> getLimits(Long customerId) {
        try {
            return billingRepo.getLimits(customerId)//TODO wrap with vavr monad
                    .map(LimitsResponse::getLimits)
                    .map(this::toMap)
                    .orElse(Collections.emptyMap());
        } catch (Exception e) {
            log.error("Failed to load limits", e);
            return Collections.emptyMap();
        }
    }

    private Map<UserFeature.Feature, Integer> toMap(Limits limits) {
        Map<UserFeature.Feature, Integer> map = new HashMap<>();
        map.put(UserFeature.Feature.eyeCatcher, limits.getEyeCatcher());
        map.put(UserFeature.Feature.pageOneAd, limits.getPageOneAd());
        map.put(UserFeature.Feature.topOfPage, limits.getTopOfPage());
        return map;
    }
}

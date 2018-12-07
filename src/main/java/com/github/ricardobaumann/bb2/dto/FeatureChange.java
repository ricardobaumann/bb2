package com.github.ricardobaumann.bb2.dto;

import com.github.ricardobaumann.bb2.model.UserFeature;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
public class FeatureChange {
    private Long customerId;
    private List<Ad> inventory;
    private UserFeature.Feature feature;
    private Integer limit;
    private Set<Long> currentAds;

    public boolean hasLimit() {
        return limit != null && limit > 0;
    }
}

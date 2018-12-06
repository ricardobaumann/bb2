package com.github.ricardobaumann.bb2.dto;

import com.github.ricardobaumann.bb2.model.UserFeature;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Builder
@Getter
public class FeatureChange {
    @NotNull
    private Long customerId;
    @NotNull
    private List<Ad> inventory;
    @NotNull
    private UserFeature.Feature feature;
    @NotNull
    private Integer limit;
    @NotNull
    private Set<Long> currentAds;
}

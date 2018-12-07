package com.github.ricardobaumann.bb2.dto;

import com.github.ricardobaumann.bb2.model.UserFeature;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class FeatureResult {
    private List<Long> adsBooked = new ArrayList<>();
    private List<Long> adsUnbooked = new ArrayList<>();
    private UserFeature.Feature feature;
}

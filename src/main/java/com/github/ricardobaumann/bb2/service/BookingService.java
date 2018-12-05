package com.github.ricardobaumann.bb2.service;

import com.github.ricardobaumann.bb2.dto.Ad;
import com.github.ricardobaumann.bb2.model.UserFeature;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookingService {
    void handleFeature(List<Ad> inventory,
                       UserFeature.Feature feature,
                       Integer limit,
                       Set<Long> currentAds) {


    }
}

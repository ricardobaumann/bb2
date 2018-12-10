package com.github.ricardobaumann.bb2.service;

import com.github.ricardobaumann.bb2.dto.Ad;
import com.github.ricardobaumann.bb2.dto.FeatureChange;
import com.github.ricardobaumann.bb2.dto.FeatureResult;
import com.github.ricardobaumann.bb2.model.UserFeature;
import com.github.ricardobaumann.bb2.repo.BookingRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
public class BookingService {

    private final BookingRepo bookingRepo;

    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    FeatureResult applyFeatureChange(FeatureChange featureChange) {
        FeatureResult.FeatureResultBuilder builder = FeatureResult.builder();
        builder.feature(featureChange.getFeature());
        if (featureChange.hasLimit() && !isEmpty(featureChange.getInventory())) {
            builder.adsBooked(featureChange.getInventory()
                    .stream()
                    .limit(featureChange.getLimit())
                    .peek(ad -> log.info("Trying to book ad {} for customerId {}", ad.getAdId(), featureChange.getCustomerId()))
                    .map(ad -> book(ad, featureChange.getCustomerId(), featureChange.getFeature()))
                    .peek(adId -> log.info("Ad {} for customerID {} was booked? {}", adId, featureChange.getCustomerId(), adId.isPresent()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()));
        } else {
            if (!featureChange.hasLimit() && !isEmpty(featureChange.getCurrentAds())) {
                builder.adsUnbooked(featureChange.getCurrentAds()//TODO sorting it?
                        .stream()
                        .limit(-featureChange.getLimit())
                        .peek(adId -> log.info("Trying to unbook ad {} for customerId {}", adId, featureChange.getCustomerId()))
                        .map(adId -> unbook(adId, featureChange.getCustomerId(), featureChange.getFeature()))
                        .peek(adId -> log.info("Ad {} for customerID {} was unbooked? {}", adId, featureChange.getCustomerId(), adId.isPresent()))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList()));
            } else {
                log.warn("Could not do anything with {}", featureChange);
            }
        }
        return builder.build();
    }

    private Optional<Long> book(Ad ad,
                                Long customerId,
                                UserFeature.Feature feature) {

        return bookingRepo.put(
                customerId,
                ad.getAdId(),
                feature.toString())
                .map(result -> {
                    log.info("Book result for ad {}, customerId {} and feature {}: {}", ad, customerId, feature, result);
                    return ad.getAdId();
                });

    }

    private Optional<Long> unbook(Long adId,
                                  Long customerId,
                                  UserFeature.Feature feature) {

        return bookingRepo.delete(
                customerId,
                adId,
                feature.toString())
                .map(result -> {
                    log.info("Unbook result for ad {}, customerId {} and feature {}: {}", adId, customerId, feature, result);
                    return adId;
                });//TODO initiator

    }
}

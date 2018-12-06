package com.github.ricardobaumann.bb2.service;

import com.github.ricardobaumann.bb2.dto.Ad;
import com.github.ricardobaumann.bb2.dto.BookingResponse;
import com.github.ricardobaumann.bb2.dto.FeatureChange;
import com.github.ricardobaumann.bb2.dto.UnbookingResponse;
import com.github.ricardobaumann.bb2.model.UserFeature;
import com.github.ricardobaumann.bb2.repo.BookingRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Service
@Validated
public class BookingService {

    private final BookingRepo bookingRepo;

    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    void applyFeatureChange(@Valid FeatureChange featureChange) {

        if (featureChange.getLimit() > 0 && !featureChange.getInventory().isEmpty()) {
            featureChange.getInventory()
                    .stream()
                    .limit(featureChange.getLimit())
                    .forEach(ad -> book(ad, featureChange.getCustomerId(), featureChange.getFeature()));
        } else {
            if (featureChange.getLimit() < 0) {
                featureChange.getCurrentAds()//TODO sorting it?
                        .stream()
                        .limit(-featureChange.getLimit())
                        .forEach(adId -> unbook(adId, featureChange.getCustomerId(), featureChange.getFeature()));
            } else {
                log.warn("Could not do anything with {}", featureChange);
            }
        }

    }

    private void book(Ad ad,
                      Long customerId,
                      UserFeature.Feature feature) {

        Optional<BookingResponse> result = bookingRepo.put(
                customerId,
                ad.getAdId(),
                feature.toString(), "initiator");

        log.info("Book result for ad {}, customerId {} and feature {}: {}", ad, customerId, feature, result);
    }

    private void unbook(Long adId,
                        Long customerId,
                        UserFeature.Feature feature) {

        Optional<UnbookingResponse> result = bookingRepo.delete(
                customerId,
                adId,
                feature.toString(),
                "initiator");//TODO initiator

        log.info("Unbook result for ad {}, customerId {} and feature {}: {}", adId, customerId, feature, result);
    }
}

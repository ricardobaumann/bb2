package com.github.ricardobaumann.bb2.repo;

import com.github.ricardobaumann.bb2.dto.BookingResponse;
import com.github.ricardobaumann.bb2.dto.UnbookingResponse;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(url = "http://mobile-ad-publish-service.service.consul/", name = "booking", decode404 = true)
public interface BookingRepo {

    @RequestMapping(value = "/customers/{customerId}/ads/{adId}/features/{feature}",
            method = RequestMethod.PUT)
    Optional<BookingResponse> put(
            @PathVariable("customerId") long customerId,
            @PathVariable("adId") Long adId,
            @PathVariable("feature") String feature,
            @Param("X-Mobile-Initiator") String initiator);

    @RequestMapping(value = "/customers/{customerId}/ads/{adId}/features/{feature}",
            method = RequestMethod.DELETE)
    Optional<UnbookingResponse> delete(
            @PathVariable("customerId") long customerId,
            @PathVariable("adId") Long adId,
            @PathVariable("feature") String feature,
            @Param("X-Mobile-Initiator") String initiator);
}

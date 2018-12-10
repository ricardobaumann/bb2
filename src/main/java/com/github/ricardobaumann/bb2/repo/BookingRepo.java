package com.github.ricardobaumann.bb2.repo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import java.util.Optional;

@FeignClient(url = "http://mobile-ad-publish-service.service.consul/", name = "booking", decode404 = true)
//@FeignClient(url = "http://localhost:8080/", name = "booking", decode404 = true)
public interface BookingRepo {

    @RequestMapping(value = "/customers/{customerId}/ads/{adId}/features/{feature}",
            headers = "X-Mobile-Initiator=indirectActionPrefix=setting_bb",
            method = RequestMethod.PUT)
    Optional<Map<String, Object>> put(
            @PathVariable("customerId") long customerId,
            @PathVariable("adId") Long adId,
            @PathVariable("feature") String feature);

    @RequestMapping(value = "/customers/{customerId}/ads/{adId}/features/{feature}",
            headers = "X-Mobile-Initiator=indirectActionPrefix=setting_bb",
            method = RequestMethod.DELETE)
    Optional<Map<String, Object>> delete(
            @PathVariable("customerId") long customerId,
            @PathVariable("adId") Long adId,
            @PathVariable("feature") String feature);
}

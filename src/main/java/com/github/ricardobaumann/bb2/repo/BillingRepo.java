package com.github.ricardobaumann.bb2.repo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@FeignClient(url = "http://billing-backend.service.consul", name = "billing", decode404 = true)
public interface BillingRepo {

    @RequestMapping("/booking-buddy-webapp/limit/customer-id/{customer-id}")
    Optional<LimitsResponse> getLimits(@PathVariable("customer-id") Long customerId);
}


package com.github.ricardobaumann.bb2.repo;

import com.github.ricardobaumann.bb2.dto.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(url = "http://inventory-list-service.service.consul/", name = "inventory", decode404 = true)
public interface InventoryRepo {

    @RequestMapping(
            value = "list?reserved=false&qualityStatus=NONE&pageNumber=1&closedDomain=false",
            method = RequestMethod.GET)
    Optional<Inventory> getInventory(
            @RequestParam("customerId") long customerId,
            @RequestParam("pageSize") int maxVehicles,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("reverseSort") boolean reverseSort);
}

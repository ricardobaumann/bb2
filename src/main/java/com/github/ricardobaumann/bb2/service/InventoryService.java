package com.github.ricardobaumann.bb2.service;

import com.github.ricardobaumann.bb2.dto.Ad;
import com.github.ricardobaumann.bb2.dto.Inventory;
import com.github.ricardobaumann.bb2.repo.InventoryRepo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepo inventoryRepo;

    public InventoryService(InventoryRepo inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    List<Ad> getInventory(long customerId) {
        return inventoryRepo.getInventory(customerId, 100, "", false)
                .map(Inventory::getVehicles)
                .orElse(Collections.emptyList());//TODO handle params and exceptions?
    }
}

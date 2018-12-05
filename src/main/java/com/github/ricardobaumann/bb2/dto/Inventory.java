package com.github.ricardobaumann.bb2.dto;

import lombok.Data;

import java.util.List;

@Data
public class Inventory {

    private List<Ad> vehicles;
    private InventoryListMetadata metaData;
}

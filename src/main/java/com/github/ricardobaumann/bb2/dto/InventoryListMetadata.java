package com.github.ricardobaumann.bb2.dto;

import lombok.Data;

@Data
public class InventoryListMetadata {

    private int pageNumber;
    private int pageSize;
    private int totalResults;
}

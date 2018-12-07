package com.github.ricardobaumann.bb2.dto;

import lombok.Data;

@Data
public class Ad {
    private Long adId;
    private String priceGros;
    private String dealerPriceGros;
    private String renewalDate;
    private String creationDate;
    private String title;
    private String subTitle;
}

package com.github.ricardobaumann.bb2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Limits {
    private Integer topOfPage;
    private Integer pageOneAd;
    private Integer eyeCatcher;
}

package com.tushar.pricing_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceResponse {

    private Integer flightInstanceId;
    private String cabinClass;
    private BigDecimal price;
    private String currency;
    private Boolean isLastDayPrice;
}

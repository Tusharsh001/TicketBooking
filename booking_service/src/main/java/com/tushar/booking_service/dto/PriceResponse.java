package com.tushar.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse {
    private Integer flightInstanceId;
    private String cabinClass;
    private BigDecimal price;
    private String currency;
    private Boolean isLastDayPrice;
}

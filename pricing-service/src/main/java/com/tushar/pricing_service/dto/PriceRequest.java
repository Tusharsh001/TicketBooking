package com.tushar.pricing_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SecondaryRow;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceRequest {
    private Integer flightInstanceId;
    private BigDecimal basePrice;
    private LocalDateTime departureDateTime;
    private String currency;
}

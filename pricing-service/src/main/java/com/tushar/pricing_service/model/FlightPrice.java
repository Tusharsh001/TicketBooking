package com.tushar.pricing_service.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Integer flightInstanceId;

    @Column(nullable = false, length = 20)
    private String cabinClass;

    @Column( nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(nullable = false)
    private Double multiplierApplied;

    @Column(nullable = false)
    private Boolean isLastDayPrice;

    @Column(nullable = false)
    private LocalDateTime calculatedAt;


    private LocalDateTime validUntil;

    @Column(nullable = false)
    private LocalDateTime departureDateTime;
}

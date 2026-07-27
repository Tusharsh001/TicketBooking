package com.tushar.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Integer userId;
    private Integer flightInstanceId;
    private String flightNumber;
    private String cabinClass;
    private List<String> seatNumbers;
    private BigDecimal totalPrice;
    private String currency;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime expiresAt;
}

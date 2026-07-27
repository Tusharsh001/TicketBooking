package com.tushar.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingRequest {
    private Integer userId;
    private Integer flightInstanceId;
    private String cabinClass;
    private List<String> seatNumbers;
    private String currency;
}

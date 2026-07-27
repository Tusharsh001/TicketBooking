package com.tushar.seating_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatAvailabilityResponse {
    private Integer flightInstanceId;
    private String cabinClass;
    private Integer totalSeats;
    private Integer availableSeats;
}

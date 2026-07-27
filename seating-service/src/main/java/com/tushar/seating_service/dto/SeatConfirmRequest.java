package com.tushar.seating_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatConfirmRequest {
    private Integer flightInstanceId;
    private String seatNumber;
    private Integer userId;
    private Long bookingId;
}

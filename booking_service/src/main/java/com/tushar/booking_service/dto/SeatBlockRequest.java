package com.tushar.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatBlockRequest {
    private Integer flightInstanceId;
    private String seatNumber;
    private Integer userId;
}

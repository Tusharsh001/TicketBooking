package com.tushar.seating_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBlockRequest {

    private Integer flightInstanceId;
    private String seatNumber;
    private Integer userId;
}

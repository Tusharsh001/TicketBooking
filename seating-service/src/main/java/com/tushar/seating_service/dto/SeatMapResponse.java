package com.tushar.seating_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatMapResponse {
    private Integer flightInstanceId;
    private Integer totalSeats;
    private Integer availableSeats;
    private Integer blockedSeats;
    private Integer bookedSeats;
    private List<SeatInfo> seats;
}

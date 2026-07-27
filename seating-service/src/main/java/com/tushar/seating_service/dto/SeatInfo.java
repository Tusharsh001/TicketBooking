package com.tushar.seating_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfo {
    private String seatNumber;
    private Integer rowNumber;
    private String columnLetter;
    private String cabinClass;
    private String status;
}

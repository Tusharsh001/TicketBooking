package com.tushar.seating_service.entity;

import com.tushar.seating_service.dto.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer flightInstanceId;

    @Column(nullable = false, length = 10)
    private String seatNumber;

    @Column(nullable = false)
    private Integer rowNumber;

    @Column(nullable = false, length = 2)
    private String columnLetter;

    @Column(nullable = false, length = 20)
    private String cabinClass;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    private Integer blockedByUserId;

    private LocalDateTime blockedAt;

    private Integer bookedByUserId;

    private Long bookingId;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;
}

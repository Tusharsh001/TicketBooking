package com.tushar.seating_service.service;

import com.tushar.seating_service.dto.*;
import com.tushar.seating_service.entity.Seat;
import com.tushar.seating_service.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    private final SeatRepository seatRepository;
    private final RedisLockService redisLockService;
    private final SeatMapGenerator seatMapGenerator;

    private static final int BLOCK_TIMEOUT_MINUTES = 10;

    @Transactional
    public void initializeSeats(SeatInitializationRequest request) {
        log.info("Initializing seats for flight: {}", request.getFlightInstanceId());

        Integer existingCount = seatRepository.countTotalSeats(request.getFlightInstanceId());
        if (existingCount != null && existingCount > 0) {
            log.warn("Seats already exist for flight: {}", request.getFlightInstanceId());
            return;
        }

        if (request.getEconomySeats() != null && request.getEconomySeats() > 0) {
            int rows = (int) Math.ceil((double) request.getEconomySeats() / 6);
            seatMapGenerator.generateSeatMap(
                    request.getFlightInstanceId(),
                    "ECONOMY",
                    rows,
                    "ABCDEF"
            );
        }

        if (request.getPremiumEconomySeats() != null && request.getPremiumEconomySeats() > 0) {
            int rows = (int) Math.ceil((double) request.getPremiumEconomySeats() / 6);
            seatMapGenerator.generateSeatMap(
                    request.getFlightInstanceId(),
                    "PREMIUM_ECONOMY",
                    rows,
                    "ABCDEF"
            );
        }

        if (request.getBusinessSeats() != null && request.getBusinessSeats() > 0) {
            int rows = (int) Math.ceil((double) request.getBusinessSeats() / 4);
            seatMapGenerator.generateSeatMap(
                    request.getFlightInstanceId(),
                    "BUSINESS",
                    rows,
                    "ABCD"
            );
        }

        if (request.getFirstClassSeats() != null && request.getFirstClassSeats() > 0) {
            int rows = (int) Math.ceil((double) request.getFirstClassSeats() / 2);
            seatMapGenerator.generateSeatMap(
                    request.getFlightInstanceId(),
                    "FIRST_CLASS",
                    rows,
                    "AB"
            );
        }

        log.info("Seats initialized successfully for flight: {}", request.getFlightInstanceId());
    }


    @Transactional
    public SeatResponse blockSeat(SeatBlockRequest request) {
        log.info("Blocking seat {} for flight: {}", request.getSeatNumber(), request.getFlightInstanceId());

        boolean locked = redisLockService.lockSeat(
                request.getFlightInstanceId(),
                request.getSeatNumber(),
                request.getUserId()
        );

        if (!locked) {
            throw new RuntimeException("Seat " + request.getSeatNumber() + " is currently being booked by another user");
        }

        try {
            int updated = seatRepository.blockSeat(
                    request.getFlightInstanceId(),
                    request.getSeatNumber(),
                    request.getUserId()
            );

            if (updated == 0) {
                throw new RuntimeException("Seat " + request.getSeatNumber() + " is no longer available");
            }

            log.info("Seat {} blocked successfully for user {}", request.getSeatNumber(), request.getUserId());

            return SeatResponse.builder()
                    .success(true)
                    .seatNumber(request.getSeatNumber())
                    .status("BLOCKED")
                    .expiresIn(BLOCK_TIMEOUT_MINUTES)
                    .build();

        } finally {
            redisLockService.unlockSeat(request.getFlightInstanceId(), request.getSeatNumber());
        }
    }

    @Transactional
    public SeatResponse confirmSeat(SeatConfirmRequest request) {
        log.info("Confirming seat {} for booking: {}", request.getSeatNumber(), request.getBookingId());

        int updated = seatRepository.confirmSeat(
                request.getFlightInstanceId(),
                request.getSeatNumber(),
                request.getUserId(),
                request.getBookingId()
        );

        if (updated == 0) {
            throw new RuntimeException("Seat " + request.getSeatNumber() + " is not blocked or already confirmed");
        }

        log.info("Seat {} confirmed successfully for booking {}", request.getSeatNumber(), request.getBookingId());

        return SeatResponse.builder()
                .success(true)
                .seatNumber(request.getSeatNumber())
                .status("BOOKED")
                .build();
    }

    @Transactional
    public SeatResponse releaseSeat(SeatReleaseRequest request) {
        log.info("Releasing seat {} for flight: {}", request.getSeatNumber(), request.getFlightInstanceId());

        int updated = seatRepository.releaseSeat(
                request.getFlightInstanceId(),
                request.getSeatNumber()
        );

        if (updated == 0) {
            throw new RuntimeException("Seat " + request.getSeatNumber() + " is not blocked");
        }

        log.info("Seat {} released successfully", request.getSeatNumber());

        return SeatResponse.builder()
                .success(true)
                .seatNumber(request.getSeatNumber())
                .status("AVAILABLE")
                .build();
    }

    @Transactional
    public void releaseExpiredBlocks() {
        LocalDateTime timeout = LocalDateTime.now().minusMinutes(BLOCK_TIMEOUT_MINUTES);
        int released = seatRepository.releaseExpiredBlocks(timeout);
        if (released > 0) {
            log.info("Released {} expired seat blocks", released);
        }
    }

    public SeatAvailabilityResponse getAvailableSeats(Integer flightId, String cabinClass) {
        Integer available = seatRepository.countAvailableSeats(flightId, cabinClass);

        return SeatAvailabilityResponse.builder()
                .flightInstanceId(flightId)
                .cabinClass(cabinClass)
                .availableSeats(available != null ? available : 0)
                .build();
    }


    public SeatMapResponse getSeatMap(Integer flightId) {
        List<Seat> seats = seatRepository.findByFlightInstanceId(flightId);

        List<SeatInfo> seatInfoList = seats.stream()
                .map(seat -> SeatInfo.builder()
                        .seatNumber(seat.getSeatNumber())
                        .rowNumber(seat.getRowNumber())
                        .columnLetter(seat.getColumnLetter())
                        .cabinClass(seat.getCabinClass())
                        .status(seat.getStatus().name())
                        .build())
                .collect(Collectors.toList());

        long available = seats.stream().filter(s -> s.getStatus() == SeatStatus.AVAILABLE).count();
        long blocked = seats.stream().filter(s -> s.getStatus() == SeatStatus.BLOCKED).count();
        long booked = seats.stream().filter(s -> s.getStatus() == SeatStatus.BOOKED).count();

        return SeatMapResponse.builder()
                .flightInstanceId(flightId)
                .totalSeats(seats.size())
                .availableSeats((int) available)
                .blockedSeats((int) blocked)
                .bookedSeats((int) booked)
                .seats(seatInfoList)
                .build();
    }

}

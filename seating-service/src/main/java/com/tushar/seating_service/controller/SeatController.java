package com.tushar.seating_service.controller;


import com.tushar.seating_service.dto.*;
import com.tushar.seating_service.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/initialize")
    public ResponseEntity<String> initializeSeats(@RequestBody SeatInitializationRequest request) {
        seatService.initializeSeats(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Seats initialized successfully for flight: " + request.getFlightInstanceId());
    }

    @PostMapping("/block")
    public ResponseEntity<SeatResponse> blockSeat(@RequestBody SeatBlockRequest request) {
        SeatResponse response = seatService.blockSeat(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm")
    public ResponseEntity<SeatResponse> confirmSeat(@RequestBody SeatConfirmRequest request) {
        SeatResponse response = seatService.confirmSeat(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/release")
    public ResponseEntity<SeatResponse> releaseSeat(@RequestBody SeatReleaseRequest request) {
        SeatResponse response = seatService.releaseSeat(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/flight/{flightId}/cabin/{cabinClass}/availability")
    public ResponseEntity<SeatAvailabilityResponse> getAvailableSeats(
            @PathVariable Integer flightId,
            @PathVariable String cabinClass) {
        SeatAvailabilityResponse response = seatService.getAvailableSeats(flightId, cabinClass);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/flight/{flightId}/map")
    public ResponseEntity<SeatMapResponse> getSeatMap(@PathVariable Integer flightId) {
        SeatMapResponse response = seatService.getSeatMap(flightId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/release-expired")
    public ResponseEntity<String> releaseExpiredBlocks() {
        seatService.releaseExpiredBlocks();
        return ResponseEntity.ok("Expired seat blocks released successfully");
    }
}

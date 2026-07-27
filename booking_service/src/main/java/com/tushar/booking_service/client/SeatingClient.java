package com.tushar.booking_service.client;

import com.tushar.booking_service.dto.SeatBlockRequest;
import com.tushar.booking_service.dto.SeatConfirmRequest;
import com.tushar.booking_service.dto.SeatReleaseRequest;
import com.tushar.booking_service.dto.SeatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SEATING-SERVICE")
public interface SeatingClient {
    @PostMapping("/api/seats/block")
    SeatResponse blockSeat(@RequestBody SeatBlockRequest request);

    @PostMapping("/api/seats/confirm")
    SeatResponse confirmSeat(@RequestBody SeatConfirmRequest request);

    @PostMapping("/api/seats/release")
    SeatResponse releaseSeat(@RequestBody SeatReleaseRequest request);
}

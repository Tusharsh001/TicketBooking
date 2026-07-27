package com.tushar.booking_service.client;

import com.tushar.booking_service.dto.FlightInstanceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "FLIGHT-OPERATION-SERVICE")
public interface FlightOpsClient {
    @GetMapping("/api/flights/{id}")
    FlightInstanceDTO getFlight(@PathVariable("id") Integer id);
}

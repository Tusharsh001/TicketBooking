package com.tushar.booking_service.client;

import com.tushar.booking_service.dto.PriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRICING-SERVICE")
public interface PricingClient {
    @GetMapping("/api/prices/flight/{flightId}/cabin/{cabinClass}")
    PriceResponse getPrice(@PathVariable("flightId") Integer flightId,
                           @PathVariable("cabinClass") String cabinClass);
}

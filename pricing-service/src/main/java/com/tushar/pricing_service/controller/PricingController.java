package com.tushar.pricing_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PricingController {


        private final Pricing pricingService;

        @PostMapping("/calculate")
        public ResponseEntity<List<PriceResponse>> calculatePrices(@RequestBody PriceCalculationRequest request) {
            List<PriceResponse> prices = pricingService.calculateAndStorePrices(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(prices);
        }

        @GetMapping("/flight/{flightId}/cabin/{cabinClass}")
        public ResponseEntity<PriceResponse> getPriceForBooking(
                @PathVariable Integer flightId,
                @PathVariable String cabinClass) {
            PriceResponse price = pricingService.getPriceForBooking(flightId, cabinClass);
            return ResponseEntity.ok(price);
        }

        @GetMapping("/flight/{flightId}")
        public ResponseEntity<List<PriceResponse>> getAllPricesForFlight(@PathVariable Integer flightId) {
            List<PriceResponse> prices = pricingService.getAllPricesForFlight(flightId);
            return ResponseEntity.ok(prices);
        }

        @DeleteMapping("/flight/{flightId}")
        public ResponseEntity<Void> deletePricesForFlight(@PathVariable Integer flightId) {
            pricingService.deletePricesForFlight(flightId);
            return ResponseEntity.noContent().build();
        }
    }


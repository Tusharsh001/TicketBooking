package com.tushar.pricing_service.service;

import com.tushar.pricing_service.dto.PriceRequest;
import com.tushar.pricing_service.dto.PriceResponse;
import com.tushar.pricing_service.mapper.PriceMapper;
import com.tushar.pricing_service.model.FlightPrice;
import com.tushar.pricing_service.repository.FlightPriceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final FlightPriceRepository priceRepository;
    private final RedisCacheService redisCacheService;

    private static final double ECONOMY_MULTIPLIER = 1.0;
    private static final double PREMIUM_ECONOMY_MULTIPLIER = 1.5;
    private static final double BUSINESS_MULTIPLIER = 2.5;
    private static final double FIRST_CLASS_MULTIPLIER = 4.0;


    @Transactional
    public List<PriceResponse> calculateAndStorePrices(PriceRequest request) {

        if (priceRepository.existsByFlightInstanceId(request.getFlightInstanceId())) {
            return getExistingPrices(request.getFlightInstanceId());
        }

        List<FlightPrice> flightPrices = new ArrayList<>();

        flightPrices.add(PriceMapper.createFlightPrice(request, "ECONOMY", ECONOMY_MULTIPLIER));
        flightPrices.add(PriceMapper.createFlightPrice(request, "PREMIUM_ECONOMY", PREMIUM_ECONOMY_MULTIPLIER));
        flightPrices.add(PriceMapper.createFlightPrice(request, "BUSINESS", BUSINESS_MULTIPLIER));
        flightPrices.add(PriceMapper.createFlightPrice(request, "FIRST_CLASS", FIRST_CLASS_MULTIPLIER));

        List<FlightPrice> savedPrices = priceRepository.saveAll(flightPrices);

        for (FlightPrice price : savedPrices) {
            redisCacheService.cachePrice(price);
        }

        return savedPrices.stream().map(PriceMapper :: toResponse).toList();
    }



    public PriceResponse getPriceForBooking(Integer flightInstanceId, String cabinClass) {
        PriceResponse cachedPrice = redisCacheService.getPriceFromCache(flightInstanceId, cabinClass);
        if (cachedPrice != null) {
            return cachedPrice;
        }

        FlightPrice flightPrice = priceRepository
                .findByFlightInstanceIdAndCabinClass(flightInstanceId, cabinClass)
                .orElseThrow(() -> new RuntimeException("Price not found for flight: " + flightInstanceId));

        redisCacheService.cachePrice(flightPrice);

        return  PriceMapper.toResponse(flightPrice);
    }



    public List<PriceResponse> getAllPricesForFlight(Integer flightInstanceId) {
        List<FlightPrice> prices = priceRepository.findByFlightInstanceId(flightInstanceId);
        return  prices.stream().map(PriceMapper::toResponse).toList();
    }

    private List<PriceResponse> getExistingPrices(Integer flightInstanceId) {
        List<FlightPrice> existingPrices = priceRepository.findByFlightInstanceId(flightInstanceId);
        return existingPrices.stream().map(PriceMapper::toResponse).toList();
    }

    @Transactional
    public void deletePricesForFlight(Integer flightInstanceId) {
        priceRepository.deleteByFlightInstanceId(flightInstanceId);
        redisCacheService.evictPricesForFlight(flightInstanceId);
    }

}

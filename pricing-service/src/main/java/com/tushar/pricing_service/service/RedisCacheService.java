package com.tushar.pricing_service.service;

import com.tushar.pricing_service.dto.PriceResponse;
import com.tushar.pricing_service.model.FlightPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String PRICE_KEY_PREFIX = "price:flight:";
    private static final long CACHE_TTL_HOURS = 1;

    public void cachePrice(FlightPrice flightPrice) {
        String key = buildKey(flightPrice.getFlightInstanceId(), flightPrice.getCabinClass());



        PriceResponse priceResponse = PriceResponse.builder()
                .flightInstanceId(flightPrice.getFlightInstanceId())
                .cabinClass(flightPrice.getCabinClass())
                .price(flightPrice.getPrice())
                .currency(flightPrice.getCurrency())
                .isLastDayPrice(flightPrice.getIsLastDayPrice())
                .build();


        redisTemplate.opsForValue().set(key, priceResponse, CACHE_TTL_HOURS, TimeUnit.HOURS);
    }

    public PriceResponse getPriceFromCache(Integer flightInstanceId, String cabinClass) {
        String key = buildKey(flightInstanceId, cabinClass);
        Object cached = redisTemplate.opsForValue().get(key);

        ///  converting it to the priceResponse Object
        ///  if it hit the cache hit
        if (cached instanceof PriceResponse) {
            return (PriceResponse) cached;
        }
        /// cache miss
        return null;
    }

    public void evictPrice(Integer flightInstanceId, String cabinClass) {
        String key = buildKey(flightInstanceId, cabinClass);
        redisTemplate.delete(key);
    }

    public void evictPricesForFlight(Integer flightInstanceId) {
        String pattern = buildKey(flightInstanceId, "*");
        redisTemplate.delete(redisTemplate.keys(pattern));
    }

    /// ////// helper functions
    private String buildKey(Integer flightInstanceId, String cabinClass) {
        return PRICE_KEY_PREFIX + flightInstanceId + ":cabin:" + cabinClass;
    }
}

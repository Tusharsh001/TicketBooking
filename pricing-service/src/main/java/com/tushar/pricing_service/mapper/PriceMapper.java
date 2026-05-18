package com.tushar.pricing_service.mapper;

import com.tushar.pricing_service.dto.PriceRequest;
import com.tushar.pricing_service.dto.PriceResponse;
import com.tushar.pricing_service.model.FlightPrice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PriceMapper {

    private static final double LAST_DAY_MARKUP = 1.4;


    public static FlightPrice createFlightPrice(PriceRequest request, String cabinClass, double multiplier) {

        /// confirming that is it the last date of booking or not
        LocalDate today = LocalDate.now();
        LocalDate departureDate = request.getDepartureDateTime().toLocalDate();

        boolean isLastDay = today.equals(departureDate);


        /// price calculation
        BigDecimal price = request.getBasePrice().multiply(BigDecimal.valueOf(multiplier));

        if (isLastDay) {
            price = price.multiply(BigDecimal.valueOf(LAST_DAY_MARKUP));
        }
        BigDecimal calculatedPrice = price.setScale(2, RoundingMode.HALF_UP);



        return FlightPrice.builder()
                .flightInstanceId(request.getFlightInstanceId())
                .cabinClass(cabinClass)
                .price(calculatedPrice)
                .currency(request.getCurrency())
                .basePrice(request.getBasePrice())
                .multiplierApplied(multiplier)
                .isLastDayPrice(isLastDay)
                .calculatedAt(LocalDateTime.now())
                .departureDateTime(request.getDepartureDateTime())
                .validUntil(request.getDepartureDateTime())
                .build();
    }


    public static PriceResponse toResponse(FlightPrice entity){

            return PriceResponse.builder()
                    .flightInstanceId(entity.getFlightInstanceId())
                    .cabinClass(entity.getCabinClass())
                    .price(entity.getPrice())
                    .currency(entity.getCurrency())
                    .isLastDayPrice(entity.getIsLastDayPrice())
                    .build();

    }


}

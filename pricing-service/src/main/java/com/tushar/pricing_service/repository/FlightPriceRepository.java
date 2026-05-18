package com.tushar.pricing_service.repository;

import com.tushar.pricing_service.model.FlightPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Repository
public interface FlightPriceRepository extends JpaRepository<FlightPrice,Integer> {

    // Find price for specific flight and cabin class
    Optional<FlightPrice> findByFlightInstanceIdAndCabinClass(int flightInstanceId, String cabinClass);

    // Find all prices for a flight
    List<FlightPrice> findByFlightInstanceId(int flightInstanceId);

    // Check if prices already exist for a flight
    boolean existsByFlightInstanceId(int flightInstanceId);

    // Delete all prices for a flight (when flight is cancelled)
    void deleteByFlightInstanceId(int flightInstanceId);

    // Get price directly (most efficient)
    @Query("SELECT fp.price FROM FlightPrice fp " +
            "WHERE fp.flightInstanceId = :flightId AND fp.cabinClass = :cabin")
    Optional<BigDecimal> findPriceByFlightIdAndCabin(@Param("flightId") int flightId,
                                                     @Param("cabin") String cabinClass);

    // Check if last day price was applied
    @Query("SELECT fp.isLastDayPrice FROM FlightPrice fp" +
            " WHERE fp.flightInstanceId = :flightId AND fp.cabinClass = :cabin")
    Optional<Boolean> isLastDayPriceApplied(@Param("flightId") int flightId,
                                            @Param("cabin") String cabinClass);
}

package com.tushar.booking_service.repository;

import com.tushar.booking_service.dto.BookingStatus;
import com.tushar.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Integer userId);

    // Find expired pending bookings
    List<Booking> findByStatusAndExpiresAtBefore(BookingStatus status, LocalDateTime time);

    // Find all bookings for a flight
    List<Booking> findByFlightInstanceId(Integer flightInstanceId);

    // Check if user has active booking for a flight
    boolean existsByUserIdAndFlightInstanceIdAndStatusIn(
            Integer userId,
            Integer flightInstanceId,
            List<BookingStatus> statuses
    );
}

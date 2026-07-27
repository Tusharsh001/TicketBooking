package com.tushar.booking_service.service;

import com.tushar.booking_service.client.FlightOpsClient;
import com.tushar.booking_service.client.PricingClient;
import com.tushar.booking_service.client.SeatingClient;
import com.tushar.booking_service.dto.*;
import com.tushar.booking_service.entity.Booking;
import com.tushar.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightOpsClient flightOpsClient;
    private final PricingClient pricingClient;
    private final SeatingClient seatingClient;

    private static final int PAYMENT_TIMEOUT_MINUTES = 10;

    @Transactional
    public CreateBookingResponse createBooking(CreateBookingRequest request) {
        log.info("Creating booking for user: {}, flight: {}", request.getUserId(), request.getFlightInstanceId());

        // 1. Get flight details
        FlightInstanceDTO flight = flightOpsClient.getFlight(request.getFlightInstanceId());
        log.debug("Flight details: {}", flight.getFlightNumber());

        // 2. Get price
        PriceResponse price = pricingClient.getPrice(
                request.getFlightInstanceId(),
                request.getCabinClass()
        );
        log.debug("Price: {}", price.getPrice());

        // 3. Block each seat
        for (String seat : request.getSeatNumbers()) {
            SeatBlockRequest blockRequest = SeatBlockRequest.builder()
                    .flightInstanceId(request.getFlightInstanceId())
                    .seatNumber(seat)
                    .userId(request.getUserId())
                    .build();
            seatingClient.blockSeat(blockRequest);
            log.debug("Seat {} blocked", seat);
        }

        // 4. Calculate total price
        BigDecimal totalPrice = price.getPrice().multiply(BigDecimal.valueOf(request.getSeatNumbers().size()));

        // 5. Create booking
        Booking booking = Booking.builder()
                .userId(request.getUserId())
                .flightInstanceId(request.getFlightInstanceId())
                .flightNumber(flight.getFlightNumber())
                .cabinClass(request.getCabinClass())
                .seatNumbers(String.join(",", request.getSeatNumbers()))
                .numberOfSeats(request.getSeatNumbers().size())
                .totalPrice(totalPrice)
                .currency(request.getCurrency())
                .status(BookingStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(PAYMENT_TIMEOUT_MINUTES))
                .build();

        Booking saved = bookingRepository.save(booking);
        log.info("Booking created with ID: {}", saved.getId());

        return CreateBookingResponse.builder()
                .bookingId(saved.getId())
                .status(saved.getStatus().name())
                .totalPrice(saved.getTotalPrice())
                .currency(saved.getCurrency())
                .expiresInMinutes(PAYMENT_TIMEOUT_MINUTES)
                .build();
    }


    @Transactional
    public BookingResponse confirmBooking(ConfirmBookingRequest request) {
        log.info("Confirming booking: {}", request.getBookingId());

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking is not pending");
        }

        if (booking.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Booking has expired");
        }

        // Confirm each seat
        String[] seats = booking.getSeatNumbers().split(",");
        for (String seat : seats) {
            SeatConfirmRequest confirmRequest = SeatConfirmRequest.builder()
                    .flightInstanceId(booking.getFlightInstanceId())
                    .seatNumber(seat)
                    .userId(booking.getUserId())
                    .bookingId(request.getBookingId())
                    .build();
            seatingClient.confirmSeat(confirmRequest);
            log.debug("Seat {} confirmed", seat);
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setConfirmedAt(LocalDateTime.now());
        booking.setPaymentId(request.getPaymentId());

        Booking updated = bookingRepository.save(booking);
        log.info("Booking confirmed: {}", updated.getId());

        return mapToResponse(updated);
    }



    public List<BookingResponse> getBookingsByUser(Integer userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void releaseExpiredBookings() {
        List<Booking> expired = bookingRepository.findByStatusAndExpiresAtBefore(
                BookingStatus.PENDING, LocalDateTime.now()
        );

        if (expired.isEmpty()) {
            return;
        }

        log.info("Found {} expired bookings", expired.size());

        for (Booking booking : expired) {
            String[] seats = booking.getSeatNumbers().split(",");
            for (String seat : seats) {
                try {
                    SeatReleaseRequest releaseRequest = SeatReleaseRequest.builder()
                            .flightInstanceId(booking.getFlightInstanceId())
                            .seatNumber(seat)
                            .build();
                    seatingClient.releaseSeat(releaseRequest);
                } catch (Exception e) {
                    log.error("Failed to release seat {} for booking {}", seat, booking.getId(), e);
                }
            }

            booking.setStatus(BookingStatus.EXPIRED);
            bookingRepository.save(booking);
        }

        log.info("Released {} expired bookings", expired.size());
    }

    @Transactional
    public void cancelBooking(Long bookingId, String reason) {
        log.info("Cancelling booking: {}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            String[] seats = booking.getSeatNumbers().split(",");
            for (String seat : seats) {
                SeatReleaseRequest releaseRequest = SeatReleaseRequest.builder()
                        .flightInstanceId(booking.getFlightInstanceId())
                        .seatNumber(seat)
                        .build();
                seatingClient.releaseSeat(releaseRequest);
                log.debug("Seat {} released", seat);
            }
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancellationReason(reason);
        bookingRepository.save(booking);

        log.info("Booking cancelled: {}", bookingId);
    }

    public BookingResponse getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return mapToResponse(booking);
    }

    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .flightInstanceId(booking.getFlightInstanceId())
                .flightNumber(booking.getFlightNumber())
                .cabinClass(booking.getCabinClass())
                .seatNumbers(Arrays.asList(booking.getSeatNumbers().split(",")))
                .totalPrice(booking.getTotalPrice())
                .currency(booking.getCurrency())
                .status(booking.getStatus().name())
                .createdAt(booking.getCreatedAt())
                .confirmedAt(booking.getConfirmedAt())
                .expiresAt(booking.getExpiresAt())
                .build();
    }


}

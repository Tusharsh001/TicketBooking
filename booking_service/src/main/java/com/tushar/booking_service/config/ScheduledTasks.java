package com.tushar.booking_service.config;

import com.tushar.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
    private final BookingService bookingService;

    // Runs every minute
    @Scheduled(fixedDelay = 60000)
    public void releaseExpiredBookings() {
        log.debug("Running scheduled task: release expired bookings");
        bookingService.releaseExpiredBookings();
    }
}

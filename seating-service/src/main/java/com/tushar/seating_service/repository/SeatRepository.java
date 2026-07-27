package com.tushar.seating_service.repository;

import com.tushar.seating_service.dto.SeatStatus;
import com.tushar.seating_service.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Integer> {

    List<Seat> findByFlightInstanceId(Integer flightInstanceId);

    List<Seat> findByFlightInstanceIdAndCabinClass(Integer flightInstanceId, String cabinClass);

    List<Seat> findByFlightInstanceIdAndCabinClassAndStatus(Integer flightInstanceId, String cabinClass, SeatStatus status);

    Optional<Seat> findByFlightInstanceIdAndSeatNumber(Integer flightInstanceId, String seatNumber);

    @Query("SELECT COUNT(s) FROM Seat s WHERE s.flightInstanceId = :flightId AND s.cabinClass = :cabin AND s.status = 'AVAILABLE'")
    Integer countAvailableSeats(@Param("flightId") Integer flightId, @Param("cabin") String cabinClass);

    //seat block
    @Modifying
    @Transactional
    @Query("UPDATE Seat s SET s.status = 'BLOCKED', s.blockedByUserId = :userId, " +
            "s.blockedAt = CURRENT_TIMESTAMP, s.lastUpdated = CURRENT_TIMESTAMP " +
            "WHERE s.flightInstanceId = :flightId AND s.seatNumber = :seatNumber AND s.status = 'AVAILABLE'")
    int blockSeat(@Param("flightId") Integer flightId,
                  @Param("seatNumber") String seatNumber,
                  @Param("userId") Integer userId);

    //seat confirm
    @Modifying
    @Transactional
    @Query("UPDATE Seat s SET s.status = 'BOOKED', s.bookedByUserId = :userId, " +
            "s.bookingId = :bookingId, s.lastUpdated = CURRENT_TIMESTAMP " +
            "WHERE s.flightInstanceId = :flightId AND s.seatNumber = :seatNumber AND s.status = 'BLOCKED'")
    int confirmSeat(@Param("flightId") Integer flightId,
                    @Param("seatNumber") String seatNumber,
                    @Param("userId") Integer userId,
                    @Param("bookingId") Long bookingId);

    // seat release
    @Modifying
    @Transactional
    @Query("UPDATE Seat s SET s.status = 'AVAILABLE', s.blockedByUserId = NULL, " +
            "s.blockedAt = NULL, s.lastUpdated = CURRENT_TIMESTAMP " +
            "WHERE s.flightInstanceId = :flightId AND s.seatNumber = :seatNumber AND s.status = 'BLOCKED'")
    int releaseSeat(@Param("flightId") Integer flightId,
                    @Param("seatNumber") String seatNumber);

    // Release expired blocked seats
    @Modifying
    @Transactional
    @Query("UPDATE Seat s SET s.status = 'AVAILABLE', s.blockedByUserId = NULL, " +
            "s.blockedAt = NULL, s.lastUpdated = CURRENT_TIMESTAMP " +
            "WHERE s.status = 'BLOCKED' AND s.blockedAt < :timeout")
    int releaseExpiredBlocks(@Param("timeout") LocalDateTime timeout);


    @Query("SELECT COUNT(s) FROM Seat s WHERE s.flightInstanceId = :flightId")
    Integer countTotalSeats(@Param("flightId") Integer flightId);

}

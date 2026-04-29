package com.tushar.AirlineCore_Service.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(unique = true,nullable = false)
    private String code;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false,length = 50)
    private String manufacture;

    @Column(nullable = false)
    private Integer seatingCapacity;

    @Column(name = "economy_seat")
    private Integer economySeats=0;

    @Column(name = "premium_economy_seat")
    private Integer premiumEconomySeats=0;

    @Column(name = "business_seat")
    private Integer businessSeats=0;



    @Column(name = "first_class_seat")
    private Integer firstClassSeats=0;

    private Integer rangeKm;
    private Integer cruisingSpeedKmh;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;


    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private AircraftStatus status=AircraftStatus.ACTIVE;

    private Boolean isAvailable=true;

    @ManyToOne
    private Airline airline;

    private int currentAirportId;


    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;


    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Integer getTotalSeats(){
        return economySeats+businessSeats+firstClassSeats
                +premiumEconomySeats;
    }

    public boolean isOperational(){
        return AircraftStatus.ACTIVE.equals(status) &&
                Boolean.TRUE.equals(isAvailable);
    }

    public boolean requiresMaintenance(){
        return nextMaintenanceDate!=null&&
                nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(2));
    }

}

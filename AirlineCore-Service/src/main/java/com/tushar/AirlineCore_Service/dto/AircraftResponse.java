package com.tushar.AirlineCore_Service.dto;

import com.tushar.AirlineCore_Service.model.AircraftStatus;
import com.tushar.AirlineCore_Service.model.Airline;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftResponse {

    private int id;
    private String code;
    private String model;
    private String manufacturer;
    private Integer seatingCapacity;
    private Integer economySeats;
    private Integer premiumEconomySeats;
    private Integer businessSeats;
    private Integer firstClassSeats;
    private Integer rangeKm;
    private Integer cruisingSpeedKmh;
    private Integer yearOfManufacture;
    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;
    private AircraftStatus status;
    private Boolean isAvailable;

    private int airlineId;
    private String airlineName;
    private String airlineIataCode;

    private int currentAirportId;
    private int currentAirportCity;
    private String currentAirportCode;
    private String currentAirportName;

    private int totalSeats;
    private boolean requiresMaintenance;
    private boolean isOperational;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.tushar.AirlineCore_Service.dto;

import com.tushar.AirlineCore_Service.model.AircraftStatus;
import com.tushar.AirlineCore_Service.model.Airline;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftRequest {

    @NotBlank(message = "Aircraft code is required")
    private String code;

    @NotBlank(message = "Aircraft model is required")
    private String model;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @NotNull(message = "Seating capacity is required")
    @Positive(message = "Seating capacity must be positive")
    private Integer seatingCapacity;

    @Positive(message = "Economy seats must be positive")
    private Integer economySeats;

    @Positive(message = "Premium Economy seats must be positive")
    private Integer premiumEconomySeats;

    @Positive(message = "Business seats must be positive")
    private Integer businessSeats;

    @Positive(message = "First class seats must be positive")
    private Integer firstClassSeats;

    @Positive(message = "Range  must be positive")
    private Integer RangeKm;
    @Positive(message = "Cruising speed must be positive")
    private Integer cruisingSpeedKmh;

    @Positive(message = "year of manufacturing must be positive")
    private Integer yearOfManufacture;


    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;

    @NotNull(message = "Status is required")
    private AircraftStatus status=AircraftStatus.ACTIVE;

    @NotNull(message = "Availability is Required")
    private Boolean isAvailable;

    private int currentAirportId;


}

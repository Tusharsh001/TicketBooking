package com.tushar.AirlineCore_Service.mapper;

import com.tushar.AirlineCore_Service.dto.AircraftRequest;
import com.tushar.AirlineCore_Service.dto.AircraftResponse;
import com.tushar.AirlineCore_Service.model.Aircraft;
import com.tushar.AirlineCore_Service.model.Airline;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AircraftMapper {

    public static Aircraft toEntity(AircraftRequest request, Airline airline){
        if(request ==null) return null;

        return Aircraft.builder()
                .code(request.getCode())
                .model(request.getModel())
                .manufacture(request.getManufacturer())
                .seatingCapacity(request.getSeatingCapacity())
                .economySeats(request.getEconomySeats())
                .premiumEconomySeats(request.getPremiumEconomySeats())
                .businessSeats(request.getBusinessSeats())
                .firstClassSeats(request.getFirstClassSeats())
                .rangeKm(request.getRangeKm())
                .cruisingSpeedKmh(request.getCruisingSpeedKmh())
                .yearOfManufacture(request.getYearOfManufacture())
                .registrationDate(request.getRegistrationDate())
                .nextMaintenanceDate(request.getNextMaintenanceDate())
                .status(request.getStatus())
                .isAvailable(request.getIsAvailable())
                .airline(airline)
                .currentAirportId(request.getCurrentAirportId())
                .build();
    }

    public static AircraftResponse toResponse(Aircraft aircraft){
        if(aircraft==null) return null;

        return AircraftResponse.builder()
                .id(aircraft.getId())
                .code(aircraft.getCode())
                .model(aircraft.getModel())
                .manufacturer(aircraft.getManufacture())
                .seatingCapacity(aircraft.getSeatingCapacity())
                .economySeats(aircraft.getEconomySeats())
                .premiumEconomySeats(aircraft.getPremiumEconomySeats())
                .businessSeats(aircraft.getBusinessSeats())
                .firstClassSeats(aircraft.getFirstClassSeats())
                .rangeKm(aircraft.getRangeKm())
                .cruisingSpeedKmh(aircraft.getCruisingSpeedKmh())
                .yearOfManufacture(aircraft.getYearOfManufacture())
                .registrationDate(aircraft.getRegistrationDate())
                .nextMaintenanceDate(aircraft.getNextMaintenanceDate())
                .status(aircraft.getStatus())
                .isAvailable(aircraft.getIsAvailable())
                //airline info
                .airlineId(aircraft.getAirline() != null? aircraft.getAirline().getId(): null)
                .airlineIataCode(aircraft.getAirline()!= null ? aircraft.getAirline().getIataCode(): null)
                .airlineName(aircraft.getAirline() != null ? aircraft.getAirline().getName(): null)
                // airport is cross-service -- only ID avaiable here
                .currentAirportId(aircraft.getCurrentAirportId())
                //computed fields
                .totalSeats(aircraft.getTotalSeats())
                .requiresMaintenance(aircraft.requiresMaintenance())
                .isOperational(aircraft.isOperational())
                //audit
                .createdAt(aircraft.getCreatedAt())
                .updatedAt(aircraft.getUpdatedAt())
                .build();


    }


    public static void updateEntity(Aircraft aircraft,AircraftRequest request){
        if(aircraft== null || request== null) return;

        aircraft.setCode(request.getCode());
        aircraft.setModel(request.getModel());
        aircraft.setManufacture(request.getManufacturer());
        aircraft.setSeatingCapacity(request.getSeatingCapacity());
        aircraft.setEconomySeats(request.getEconomySeats());
        aircraft.setPremiumEconomySeats(request.getPremiumEconomySeats());
        aircraft.setBusinessSeats(request.getBusinessSeats());
        aircraft.setFirstClassSeats(request.getFirstClassSeats());
        aircraft.setRangeKm(request.getRangeKm());
        aircraft.setCruisingSpeedKmh(request.getCruisingSpeedKmh());
        aircraft.setYearOfManufacture(request.getYearOfManufacture());
        aircraft.setRegistrationDate(request.getRegistrationDate());
        aircraft.setUpdatedAt(LocalDateTime.now());
        aircraft.setNextMaintenanceDate(request.getNextMaintenanceDate());
        aircraft.setStatus(request.getStatus());
        aircraft.setIsAvailable(request.getIsAvailable());
        aircraft.setCurrentAirportId(request.getCurrentAirportId());
    }
}

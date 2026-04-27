package com.tushar.location_service.mapper;


import com.tushar.location_service.dto.AirPortRequest;
import com.tushar.location_service.dto.AirportResponse;
import com.tushar.location_service.model.AirPort;
import com.tushar.location_service.model.City;

public class AirportMapper {

    public static AirPort toEntity(AirPortRequest request){
        if(request==null) return null;

        return AirPort.builder()
                .iataCode(request.getIataCode())
                .name(request.getName())
                //.timeZoneId(request.getIataCode())
                .address(request.getAddress())
                .geoCode(request.getGeoCode())
                .build();
    }

    public static AirportResponse toResponse(AirPort airPort){
        if(airPort==null) return null;
        return AirportResponse.builder()
                .id(airPort.getId())
                .iataCode(airPort.getIataCode())
                .name(airPort.getName())
                .detailedName(airPort.getDetailedName())
                //.timeZone(airPort.getTimeZone())
                .address(airPort.getAddress())
                .city(CityMapper.toResponse(airPort.getCity()))
                .geoCode(airPort.getGeoCode())
                .build();
    }

    public static void updateEntity(AirPortRequest request,AirPort existingAirport) {

        if(request==null || existingAirport==null ) return;

        if(request.getIataCode()!=null) existingAirport.setIataCode(request.getIataCode());

        if(request.getName()!= null) existingAirport.setName(request.getName());

        if(request.getAddress()!= null) existingAirport.setAddress(request.getAddress());

        if(request.getGeoCode()!= null ) existingAirport.setGeoCode(request.getGeoCode());
    }
}

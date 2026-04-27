package com.tushar.location_service.dto;

import com.tushar.location_service.model.Address;
import com.tushar.location_service.model.GeoCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportResponse {
    private int id;
    private String iataCode;
    private String name;
    private String detailedName;
    private ZoneId timeZone;
    private Address address;
    private CityResponse city;
    private GeoCode geoCode;
}

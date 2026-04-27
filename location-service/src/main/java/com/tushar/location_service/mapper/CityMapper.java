package com.tushar.location_service.mapper;

import com.tushar.location_service.dto.CityRequest;
import com.tushar.location_service.dto.CityResponse;
import com.tushar.location_service.model.City;

public class CityMapper {

    public static City toEntity(CityRequest request){
        if(request==null) return null;
        return City.builder().
                name(request.getName())
                .cityCode(request.getCityCode())
                .countryCode(request.getCountryCode())
                .countryName(request.getCountryName())
                .regionCode(request.getRegionCode())
                .timezoneId(request.getTimezoneOffSet())
                .build();

    }

    public static CityResponse toResponse(City city){
        if(city==null) return null;
         return CityResponse.builder()
                 .id(city.getId())
                 .name(city.getName())
                 .cityCode(city.getCityCode())
                 .countryCode(city.getCountryCode())
                 .countryName(city.getCountryName())
                 .regionCode(city.getRegionCode())
                 .build();
    }

    public static City updateEntity(City city, CityRequest request){
        if(request.getName()!= null) {
            city.setName(request.getName());
        }
        if(request.getCityCode()!= null) {
            city.setCityCode(request.getCityCode());
        }
        if(request.getCountryCode()!= null) {
            city.setCountryCode(request.getCountryCode());
        }
        if(request.getCountryName()!= null) {
            city.setCountryName(request.getCountryName());
        }
        if(request.getRegionCode()!= null) {
            city.setRegionCode(request.getRegionCode());
        }

        return city;

    }
}

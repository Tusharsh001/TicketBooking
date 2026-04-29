package com.tushar.AirlineCore_Service.mapper;

import com.tushar.AirlineCore_Service.dto.AirlineRequest;
import com.tushar.AirlineCore_Service.dto.AirlineResponse;
import com.tushar.AirlineCore_Service.model.Airline;
import com.tushar.AirlineCore_Service.model.Support;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

public class AirlineMapper {

    public static Airline toEntity(AirlineRequest request,int ownerId){
        if(request==null) return null;

        Airline airline= Airline.builder()
                .iataCode(request.getIataCode())
                .icaoCode(request.getIcaoCode())
                .name(request.getName())
                .alias(request.getAlias())
                .logoUrl(request.getLogourl())
                .website(request.getWebsite())
                .status(request.getStatus())
                .alliance(request.getAlliance())
                .headquatersCityId(request.getHeadQuatersCityId())
                .ownerId(ownerId)
                .build();
        if(request.getSupportEmail()!=null
        || request.getSupportPhone()!=null
        || request.getSupportHour()!=null){
            airline.setSupport(
                    Support.builder()
                            .email(request.getSupportEmail())
                            .phone(request.getSupportPhone())
                            .hour(request.getSupportHour()).build()
            );
        }
        return airline;
    }

    public static AirlineResponse toResponse(Airline airline){

        if(airline==null) return null;

        return AirlineResponse.builder()
                .id(airline.getId())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .name(airline.getName())
                .alias(airline.getAlias())
                .logourl(airline.getLogoUrl())
                .website(airline.getWebsite())
                .status(airline.getStatus())
                .alliance(airline.getAlliance())
                .support(airline.getSupport())
                .owenerId(airline.getOwnerId())
                .updatedById(airline.getUpdatedById())
                .updatedAt(airline.getUpdatedAt())
                .createdAt(airline.getCreatedAt())
                .build();
    }

    public static void updateEntity(Airline airline,AirlineRequest request){
        if (airline==null || request== null) return;

        airline.setIcaoCode(request.getIcaoCode());
        airline.setIataCode(request.getIataCode());
        airline.setName(request.getName());
        airline.setAlias(request.getAlias());
        airline.setLogoUrl(request.getLogourl());
        airline.setWebsite(request.getWebsite());
        airline.setStatus(request.getStatus());
        airline.setAlliance(request.getAlliance());
        airline.setUpdatedAt(LocalDateTime.now());
        airline.setHeadquatersCityId(request.getHeadQuatersCityId());
        if(airline.getSupport()==null){
            airline.setSupport(new Support());
        }
        airline.getSupport().setEmail(request.getSupportEmail());
        airline.getSupport().setPhone(request.getSupportPhone());
        airline.getSupport().setHour(request.getSupportHour());

    }
}

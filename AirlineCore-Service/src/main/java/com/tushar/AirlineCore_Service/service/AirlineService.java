package com.tushar.AirlineCore_Service.service;

import com.tushar.AirlineCore_Service.dto.AirlineRequest;
import com.tushar.AirlineCore_Service.dto.AirlineResponse;
import com.tushar.AirlineCore_Service.mapper.AirlineMapper;
import com.tushar.AirlineCore_Service.model.Airline;
import com.tushar.AirlineCore_Service.model.AirlineStatus;
import com.tushar.AirlineCore_Service.model.DropDownItems;
import com.tushar.AirlineCore_Service.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public AirlineResponse createAirline(AirlineRequest request ,int ownerId){
        Airline airline= AirlineMapper.toEntity(request,ownerId);
        airline.setCreatedAt(LocalDateTime.now());
        airline.setUpdatedAt(LocalDateTime.now());
        Airline savedAirline =airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }


    public AirlineResponse getAirLineByOwnerId(int ownerId) throws Exception{
        Airline airline =airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("no Airline exist for the given owner id"));
        return AirlineMapper.toResponse(airline);
    }


    public AirlineResponse getAirLineById(int id )throws Exception{
      Airline airline=airlineRepository.findById(id).orElseThrow(
              () -> new Exception("No airline exits for the given id")
      );
      return AirlineMapper.toResponse(airline);
    }

    public AirlineResponse updateAirline(AirlineRequest request,int ownerId)throws Exception{
        Airline airline =airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("no Airline exist for the given owner id"));
         AirlineMapper.updateEntity(airline,request);
         Airline savedAirline=airlineRepository.save(airline);
         return AirlineMapper.toResponse(savedAirline);
    }

    public List<AirlineResponse> getAllAirline(){
         return airlineRepository.findAll().stream().map(
                 AirlineMapper::toResponse
         ).toList();
    }

    public void deleteAirline(int id,int ownerId)throws Exception{
        Airline airline =airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("no Airline exist for the given owner id"));
        airlineRepository.delete(airline);
    }

    public AirlineResponse changeStatusByAdmin(int airlineId, AirlineStatus status)throws Exception{
        Airline airline=airlineRepository.findById(airlineId).orElseThrow(
                () -> new Exception("No airline exits for the given id")
        );
        airline.setStatus(status);
        Airline savedAirline=airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    public List<DropDownItems> getAirlineDropDown(){
        return airlineRepository.findByStatus(AirlineStatus.ACTIVE)
                 .stream()
                 .map(a -> DropDownItems.builder()
                         .id(a.getId())
                         .name(a.getName())
                         .iataCode(a.getIataCode())
                         .logoUrl(a.getLogoUrl())
                         .build()).toList();

    }
}

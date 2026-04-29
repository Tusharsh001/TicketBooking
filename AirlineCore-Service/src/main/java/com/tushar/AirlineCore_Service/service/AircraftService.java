package com.tushar.AirlineCore_Service.service;

import com.tushar.AirlineCore_Service.dto.AircraftRequest;
import com.tushar.AirlineCore_Service.dto.AircraftResponse;
import com.tushar.AirlineCore_Service.mapper.AircraftMapper;
import com.tushar.AirlineCore_Service.model.Aircraft;
import com.tushar.AirlineCore_Service.model.Airline;
import com.tushar.AirlineCore_Service.repository.AircraftRepository;
import com.tushar.AirlineCore_Service.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;


    public AircraftResponse createAircraft(AircraftRequest request,int ownerId)throws Exception{
      Airline airline=airlineRepository.findByOwnerId(ownerId).orElseThrow(
              ()-> new Exception("Ariline not exist for the onwer Id")
      );

        Aircraft aircraft= AircraftMapper.toEntity(request,airline);
        if(aircraftRepository.existsByCode(aircraft.getCode())){
            throw new Exception("code already exist with another aircraft");
        }
        if(aircraft.getSeatingCapacity()<aircraft.getTotalSeats()){
            throw new Exception("seating capacity can't exceed to total seats");
        }
        aircraft.setCreatedAt(LocalDateTime.now());
        aircraft.setUpdatedAt(LocalDateTime.now());
        AircraftResponse savedAircraft=AircraftMapper.toResponse(aircraftRepository.save(aircraft));
        return savedAircraft;
    }

    public AircraftResponse getAircraftById(int id) throws Exception{
        Aircraft aircraft=aircraftRepository.findById(id).orElseThrow(
                ()->
                    new Exception("Aircraft does not exist with the given id")

        );
        return AircraftMapper.toResponse(aircraft);
    }

    public List<AircraftResponse> listAllAircraftByOwner(int ownerId)throws Exception{
        Airline airline=airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("This owner don't have any airline")
        );

        return aircraftRepository.findByAirlineId(airline.getId()).stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    public AircraftResponse updateAircraft(int id,AircraftRequest request,int ownerId)throws Exception{
        Airline airline=airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("This owner don't have any airline")
        );
        Aircraft aircraft=aircraftRepository.findByIdAndAirlineId(id,airline.getId());
        if(aircraft==null){
            throw new Exception("Aircraft not exist with id");
        }
        AircraftMapper.updateEntity(aircraft,request);
        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    public void deleteAircraft(int id,int ownerId)throws Exception{
        Airline airline=airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("This owner don't have any airline")
        );

        Aircraft aircraft=aircraftRepository.findByIdAndAirlineId(id,airline.getId());
        if(aircraft==null){
            throw new Exception("Aircraft not exist with id");
        }
        aircraftRepository.delete(aircraft);
    }
}

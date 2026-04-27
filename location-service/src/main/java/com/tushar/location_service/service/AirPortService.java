package com.tushar.location_service.service;

import com.tushar.location_service.dto.AirPortRequest;
import com.tushar.location_service.dto.AirportResponse;
import com.tushar.location_service.mapper.AirportMapper;
import com.tushar.location_service.model.AirPort;
import com.tushar.location_service.model.City;
import com.tushar.location_service.repository.AirportRepository;
import com.tushar.location_service.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirPortService {

    AirportRepository airportRepository;
    private final CityRepository cityRepository;

    public AirPortService(AirportRepository airportRepository,CityRepository cityRepository){
        this.cityRepository=cityRepository;
        this.airportRepository=airportRepository;
    }

    public AirportResponse createAirport(AirPortRequest request) throws Exception{

        if(airportRepository.findByIataCode(request.getIataCode()).isPresent()){
            throw new Exception("Airport with Iata Code Already exist");
        }

        City city=cityRepository.findById(request.getCityId())
                .orElseThrow(()->
                    new Exception("City Does not Exist")

                );
        AirPort airPort= AirportMapper.toEntity(request);
        airPort.setCity(city);

       AirPort savedAirport= airportRepository.save(airPort);

       return AirportMapper.toResponse(savedAirport);

    }

    public AirportResponse getAiportById(int id) throws Exception{
       return AirportMapper.toResponse(airportRepository.findById(id).orElseThrow(()-> new  Exception("No Airport Exists with provided ID")));

    }

    public List<AirportResponse> getAllAirport(){
       return airportRepository.findAll().stream()
               .map(AirportMapper::toResponse).toList();
    }


    public AirportResponse updateAirport(int id, AirPortRequest request)throws Exception{
      AirPort existingAirport=airportRepository.findById(id).orElseThrow(
              ()-> new Exception("airport does not exist with this id")
      );
      if(request.getIataCode()==null &&
      !existingAirport.getIataCode().equals(request.getIataCode())
      && airportRepository.findByIataCode(request.getIataCode()).isPresent()){
        throw new Exception("Airport with Iata Code Already Exits");
      }
      AirportMapper.updateEntity(request,existingAirport);
      AirPort updatedAirport=airportRepository.save(existingAirport);
      return AirportMapper.toResponse(updatedAirport);
    }

    public void deleteAirport(int id) throws Exception{
      AirPort airPort=airportRepository.findById(id).orElseThrow(
              ()-> new Exception("The airport does not exists")
      );
      airportRepository.delete(airPort);
    }

    public List<AirportResponse> getAirPortByCityId(int id){
      return airportRepository.findByCityId(id).stream()
              .map(AirportMapper::toResponse).toList();
    }


}

package com.tushar.location_service.service;

import com.tushar.location_service.dto.CityRequest;
import com.tushar.location_service.dto.CityResponse;
import com.tushar.location_service.mapper.CityMapper;
import com.tushar.location_service.model.City;
import com.tushar.location_service.repository.CityRepository;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    CityRepository cityRepository;

    public CityService(CityRepository cityRepository){
        this.cityRepository=cityRepository;
    }

    public CityResponse createCity(CityRequest request) throws Exception {
      if(cityRepository.existsByCityCode(request.getCityCode())){
          throw new Exception("City with the given code already exits");
      }

        City city= CityMapper.toEntity(request);
        City result=cityRepository.save(city);
        return  CityMapper.toResponse(result);
    }

    public CityResponse getCityById(int id) throws Exception {
      City city=cityRepository.findById(id).orElseThrow(()->
              new Exception("city does not exits with the give id"));
      return CityMapper.toResponse(city);
    }

    public CityResponse updateCity(int id, CityRequest request) throws Exception{
      City city=cityRepository.findById(id).orElseThrow(()->
        new Exception("no Exits with the given id "));

      if(cityRepository.existsByCityCode(request.getCityCode())){
          throw new Exception("City with given code already exits");
      }

      City updatedCity=cityRepository.save(CityMapper.updateEntity(city,request));
      return CityMapper.toResponse(updatedCity);

    }

    public void deleteCity(int id) throws  Exception{
        City city=cityRepository.findById(id).orElseThrow(()->
                new Exception("no Exits with the given id "));
        cityRepository.delete(city);

    }

    public List<CityResponse> getAllCity(){
     return  cityRepository.findAll().stream()
             .map(CityMapper::toResponse)
             .toList();
    }

    public List<CityResponse> searchCities( String keyword){
     return cityRepository.searchByKeyword(keyword).stream()
             .map(CityMapper ::toResponse)
             .toList();
    }

    public List<CityResponse> getCityByCountryCode(String countryCode){

          return cityRepository.findByCountryCodeIgnoreCase(countryCode).stream()
                  .map(CityMapper::toResponse).toList();
    }

    public boolean cityExists(String cityCode){
      return cityRepository.existsByCityCode(cityCode);
    }


}

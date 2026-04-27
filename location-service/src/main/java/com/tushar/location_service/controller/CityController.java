package com.tushar.location_service.controller;

import com.tushar.location_service.dto.CityRequest;
import com.tushar.location_service.dto.CityResponse;
import com.tushar.location_service.service.CityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService){
        this.cityService=cityService;
    }


    @PostMapping
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest request)throws Exception{
        CityResponse response=cityService.createCity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable int id)throws Exception{
        CityResponse response=cityService.getCityById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<CityResponse>> getAllCities()throws Exception{
        List<CityResponse> response=cityService.getAllCity();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/id")
    public ResponseEntity<CityResponse> updateCity(@PathVariable int id,@Valid @RequestBody CityRequest request)throws Exception{
        CityResponse response=cityService.updateCity(id,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteCity(@PathVariable int id,@Valid @RequestBody CityRequest request)throws Exception{
        cityService.deleteCity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/search")
    public ResponseEntity<List<CityResponse>> search(@RequestParam String keyword){
        return  ResponseEntity.status(HttpStatus.FOUND).body(cityService.searchCities(keyword));
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<List<CityResponse>> getCitiesByCountryCode(@PathVariable String countryCode){
        return ResponseEntity.ok().body(cityService.getCityByCountryCode(countryCode));

    }

    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<Boolean> checkCityExists(@PathVariable String cityCode){
        return ResponseEntity.ok().body(cityService.cityExists(cityCode));
    }

}

package com.tushar.location_service.controller;

import com.tushar.location_service.dto.AirPortRequest;
import com.tushar.location_service.dto.AirportResponse;
import com.tushar.location_service.model.AirPort;
import com.tushar.location_service.service.AirPortService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirPortService airPortService;

    @PostMapping
    public ResponseEntity<AirportResponse> addAirport(@Valid @RequestBody AirPortRequest request)throws Exception{
        return ResponseEntity.ok().body(airPortService.createAirport(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getAirportById(@PathVariable int id )throws  Exception{
        return new ResponseEntity<>(airPortService.getAiportById(id), HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<List<AirportResponse>> getAllAirport(){
        return ResponseEntity.ok().body(airPortService.getAllAirport());

    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<AirportResponse>> getAirportByCityId(@PathVariable int cityId){
        return ResponseEntity.ok(airPortService.getAirPortByCityId(cityId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportResponse> updateAirport(@PathVariable int id,
                                                         @Valid @RequestBody AirPortRequest request) throws Exception{
        return ResponseEntity.ok(airPortService.updateAirport(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAirport(@PathVariable int id){
        return ResponseEntity.ok().body("Airport deleted successfully");
    }




}

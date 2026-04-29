package com.tushar.AirlineCore_Service.controller;

import com.tushar.AirlineCore_Service.dto.AircraftRequest;
import com.tushar.AirlineCore_Service.dto.AircraftResponse;
import com.tushar.AirlineCore_Service.service.AircraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aircrafts")
public class AircraftController {

    private final AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(@Valid @RequestBody AircraftRequest request,
    @RequestHeader("X-User-Id") int ownerId)throws Exception{
        AircraftResponse aircraft=aircraftService.createAircraft(request,ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(@PathVariable int id)throws Exception{
        return ResponseEntity.ok().body(aircraftService.getAircraftById(id));
    }

    @GetMapping
    public ResponseEntity<List<AircraftResponse>> getAllAircrafts(
            @RequestHeader("X-User-Id") int userId)throws Exception{
        return ResponseEntity.ok().body(aircraftService.listAllAircraftByOwner(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(
            @PathVariable int id,
            @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") int ownerId)throws Exception{
        return ResponseEntity.ok().body(aircraftService.updateAircraft(id,request,ownerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable int id,
    @RequestHeader("X-User-Id") int ownerId)throws Exception{
        aircraftService.deleteAircraft(id,ownerId);
        return ResponseEntity.ok().build();
    }


}

package com.tushar.AirlineCore_Service.controller;

import com.tushar.AirlineCore_Service.dto.AirlineRequest;
import com.tushar.AirlineCore_Service.dto.AirlineResponse;
import com.tushar.AirlineCore_Service.model.AirlineStatus;
import com.tushar.AirlineCore_Service.model.DropDownItems;
import com.tushar.AirlineCore_Service.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airlines")
public class AirlineController {

    private final AirlineService airlineService;


    @PostMapping
    public ResponseEntity<AirlineResponse> createAirline(@Valid  @RequestBody AirlineRequest request,
                                                         @RequestHeader("X-User-Id") int userId){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                airlineService.createAirline(request,userId)
        );

    }


    @GetMapping("/admin")
    public ResponseEntity<AirlineResponse> getAirlineByOwnerId(@RequestHeader("X-User-Id") int userId) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(
                airlineService.getAirLineByOwnerId(userId)
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponse> getAirlineById(@PathVariable("id") int userId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(
                airlineService.getAirLineById(userId)
        );

    }

    @GetMapping
    public ResponseEntity<List<AirlineResponse>> getAllAirline(){
        return ResponseEntity.ok().body(airlineService.getAllAirline());
    }

    @GetMapping("/dropdown")
    public ResponseEntity<List<DropDownItems>> getDropDown(){
        return ResponseEntity.ok(airlineService.getAirlineDropDown());
    }

    @PutMapping
    public ResponseEntity<AirlineResponse> updateAirline(@Valid @RequestBody AirlineRequest request,
    @RequestHeader("X-User-Id") int userId)throws Exception{
        return ResponseEntity.ok(airlineService.updateAirline(request,userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirline(@PathVariable int id ,
                                              @RequestHeader("X-User-Id") int ownerId)throws Exception{
        airlineService.deleteAirline(id,ownerId);
       return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<AirlineResponse> approveAirline(@PathVariable int id)throws Exception{
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(id, AirlineStatus.ACTIVE));
    }

    @PostMapping("/{id}/suspend")
    public ResponseEntity<AirlineResponse> suspendAirline(@PathVariable int id)throws Exception{
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(id, AirlineStatus.INACTIVE));
    }

    @PostMapping("/{id}/ban")
    public ResponseEntity<AirlineResponse> banAirline(@PathVariable int id)throws Exception{
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(id, AirlineStatus.BANNED));
    }
}

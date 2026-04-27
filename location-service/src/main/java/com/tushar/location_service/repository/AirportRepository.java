package com.tushar.location_service.repository;

import com.tushar.location_service.dto.AirPortRequest;
import com.tushar.location_service.dto.AirportResponse;
import com.tushar.location_service.model.AirPort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<AirPort ,Integer> {

    Optional<AirPort> findByIataCode(String iataCode);

    List<AirPort> findByCityId(int id);

}

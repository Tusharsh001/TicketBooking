package com.tushar.AirlineCore_Service.repository;

import com.tushar.AirlineCore_Service.dto.AircraftResponse;
import com.tushar.AirlineCore_Service.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AircraftRepository extends JpaRepository<Aircraft,Integer> {

    List<Aircraft> findByAirlineId(int airlineId);
    boolean existsByCode(String code);
    Aircraft findByIdAndAirlineId(int id,int airlineId);

}

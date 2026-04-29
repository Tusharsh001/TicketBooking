package com.tushar.AirlineCore_Service.repository;

import com.tushar.AirlineCore_Service.model.Airline;
import com.tushar.AirlineCore_Service.model.AirlineStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AirlineRepository extends JpaRepository<Airline,Integer> {


    Optional<Airline> findByOwnerId(Integer ownerId);

    List<Airline>  findByStatus(AirlineStatus status);
}

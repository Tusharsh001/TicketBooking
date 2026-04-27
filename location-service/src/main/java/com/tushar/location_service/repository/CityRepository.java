package com.tushar.location_service.repository;

import com.tushar.location_service.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CityRepository extends JpaRepository<City,Integer> {

    boolean existsByCityCode(String cityCode);
    boolean existsByCityCodeAndIdNot(String cityCode, int id);
    List<City> findByCountryCodeIgnoreCase(String countryCode);

    @Query("""
            select c from City c
            Where lower(c.name) like lower(concat('%',:keyword,'%'))
             Or lower(c.cityCode) like lower(concat('%',:keyword,'%'))
             Or lower(c.countryCode) like lower(concat('%',:keyword,'%'))
             Or lower(c.countryName) like lower(concat('%',:keyword,'%'))
             Or lower(c.regionCode) like lower(concat('%',:keyword,'%'))
            """)
    List<City> searchByKeyword(String keyword);
}

package com.tushar.location_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class AirPort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true , nullable = false,length = 3)
    private String iataCode;

    @Column(nullable = false)
    private  String name;

    private String detailedName;

    @Embedded
    private Address address;

    @Embedded
    private GeoCode geoCode;

    @Column(length = 50)
    private String timeZoneId;

    @ManyToOne
    @JsonIgnore
    private City city;

    @JsonIgnore
    @Transient
    public String getDetailedName(){
        if(city!=null && city.getCountryCode()!= null)
            return name.toUpperCase()+ "\n"+ city.getCityCode();
        return name.toUpperCase();
    }
}


package com.tushar.AirlineCore_Service.dto;

import com.tushar.AirlineCore_Service.model.AirlineStatus;
import com.tushar.AirlineCore_Service.model.Support;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineResponse {

    private int id;

    private String iataCode;
    private String icaoCode;

    private String name;
    private String alias;

    private String logourl;
    private String website;

    private AirlineStatus status;
    private String alliance;

    private int owenerId;
    private UserDTO owner;
    private int updatedById;

    private CityResponse headQuatersCity;
    private Support support;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

}

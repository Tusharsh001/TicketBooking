package com.tushar.location_service.dto;

import com.tushar.location_service.model.Address;
import com.tushar.location_service.model.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirPortRequest {

    @NotBlank(message = "IATA code is mandatory")
    @Size(min=3, max = 3, message = "IATA code must be exactly 3 Character")
    private String iataCode;

    @NotBlank(message = "Airport name is mandatory")
    private String name;

    private ZoneId timeZone;
    @Valid
    private Address address;

    @NotNull(message = "City ID is mandatory")
    private int cityId;
    @Valid
    private GeoCode geoCode;

}

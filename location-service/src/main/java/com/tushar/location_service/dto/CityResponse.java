package com.tushar.location_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {

    private int id;
    private String name;
    private String cityCode;
    private String countryName;
    private String countryCode;
    private String regionCode;
    private String timeZoneOffSet;
}

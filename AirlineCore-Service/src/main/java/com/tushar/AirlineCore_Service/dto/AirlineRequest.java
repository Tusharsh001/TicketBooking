package com.tushar.AirlineCore_Service.dto;

import com.tushar.AirlineCore_Service.model.AirlineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineRequest {
    @NotBlank(message = "iata code is mandatory")
    @Size(min=2, max=2, message = "IATA code must be exactly 2 characters")
    private String iataCode;

    @NotBlank(message = "icao code is mandatory")
    @Size(min=3, max = 3, message = "ICAO code must be exactly 3 characters")
    private String icaoCode;

    @NotBlank(message = "airline name is mandaotry")
    private String name;

    private String alias;

    private String logourl;
    private String website;

    private AirlineStatus status;

    private String alliance;
     private int headQuatersCityId;

     private String supportEmail;
     private String supportPhone;
     private String supportHour;

}

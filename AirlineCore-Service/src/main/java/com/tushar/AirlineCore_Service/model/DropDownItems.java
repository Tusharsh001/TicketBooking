package com.tushar.AirlineCore_Service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DropDownItems {
    private int id;
    private  String name;
    private String iataCode;
    private String logoUrl;
    private String country;

}

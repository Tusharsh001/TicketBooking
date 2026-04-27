package com.tushar.location_service.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class GeoCode {

    private  String latitude;
    private String longitude;
}

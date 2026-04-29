package com.tushar.AirlineCore_Service.model;

import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Support {

    private String email;
    private String phone;
    private String hour;
}

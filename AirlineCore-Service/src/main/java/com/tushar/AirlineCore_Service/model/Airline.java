package com.tushar.AirlineCore_Service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Airline {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    int id;

    @Column(unique = true, nullable = true)
    private String iataCode;

    @Column(unique = true,nullable = false)
    private String icaoCode;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private int ownerId;

    private String alias;
    private String logoUrl;
    private String website;

    @Enumerated(EnumType.STRING)
    private AirlineStatus status=AirlineStatus.ACTIVE;

    private String alliance;

    private int headquatersCityId;

    @Embedded
    private Support support;


    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;


    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private int updatedById;



}

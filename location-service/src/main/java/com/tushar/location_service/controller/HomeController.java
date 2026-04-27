package com.tushar.location_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String homeController(){
       return "Hello Welcome to The Airline_service";
    }
}

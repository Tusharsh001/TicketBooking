package com.tushar.seating_service.service;

import com.tushar.seating_service.dto.SeatStatus;
import com.tushar.seating_service.entity.Seat;
import com.tushar.seating_service.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatMapGenerator {

    private final SeatRepository seatRepo;

    public void generateSeatMap(int flightId, String cabinClass, int rows,String columns){

        List<Seat> seats=new ArrayList<>();

        char[] letters=columns.toCharArray();

        for(int row=1;row<=rows;row++){
            for(char ch:letters){
                 Seat seat= Seat.builder()
                         .flightInstanceId(flightId)
                         .seatNumber(""+row+ch)
                         .rowNumber(row)
                         .columnLetter(ch+"")
                         .cabinClass(cabinClass)
                         .status(SeatStatus.AVAILABLE)
                         .lastUpdated(LocalDateTime.now())
                         .build();
                 seats.add(seat);
            }
        }
        seatRepo.saveAll(seats);
    }
}

package com.abn.airline.dto;

import java.time.LocalTime;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineSearchResponseDTO {
    private String airlineNumber;
    private String origin;
    private String destination;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Double fare;
}

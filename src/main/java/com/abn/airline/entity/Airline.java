package com.abn.airline.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FLIGHT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "FLIGHT_NUMBER",unique = true)
    private String airlineNumber;
    @Column(name = "ORIGIN")
    private String origin;
    @Column(name = "DESTINATION")
    private String destination;
    @Column(name = "DEPARTURE_TIME")
    private LocalTime departureTime;
    @Column(name = "ARRIVAL_TIME")
    private LocalTime arrivalTime;
    @Column(name = "FARE")
    private Double fare;
}

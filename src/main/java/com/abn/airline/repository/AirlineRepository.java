package com.abn.airline.repository;

import com.abn.airline.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
    @Query(value = "SELECT * FROM FLIGHT f WHERE f.origin=?1 AND f.destination=?2", nativeQuery = true)
    List<Airline> findByOriginAndDestination(String origin, String destination);
}

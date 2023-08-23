package com.abn.airline.service;

import com.abn.airline.dto.AirlineSearchResponseDTO;

import java.util.List;
import java.util.Optional;

public interface AirlineService {
    public List<AirlineSearchResponseDTO> findFilteredAirlines(String origin, String destination,
                                                                        String sortColumn, String sortType);
}

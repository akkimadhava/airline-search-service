package com.abn.airline.api;

import com.abn.airline.dto.AirlineSearchResponseDTO;
import com.abn.airline.service.AirlineService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/airline/v1")
@Slf4j
public class AirlineSearchController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AirlineSearchController.class);

    private AirlineService airlineService;

    @Autowired
    public AirlineSearchController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }


    /**
     * Get Api to fetch Airlines details
     *
     * @param origin
     * @param destination
     * @param sortColumn
     * @param sortType
     * Fields are
     * origin : required, should not be empty or Null
     * destination : required,should not be empty or Null
     * sortColumn : to sort airline details on price/duration
     * sortType : optional to sort airline details with ASCE/DESC
     * @return list of AirlineSearchResponseDTO
     */
    @GetMapping("/search")
    public ResponseEntity<List<AirlineSearchResponseDTO>> getAirlinesByCriteria(@RequestParam @Valid String origin, @Valid @RequestParam String destination,
                                                                                @RequestParam(value = "sortColumn", required = false) String sortColumn,
                                                                                @RequestParam(value = "sortType", required = false) String sortType) throws Exception {
        log.info("In AirlineSearchController, start getAirlinesByCriteria for origin:{} , destination {}", origin, destination);
        List<AirlineSearchResponseDTO> result = airlineService.findFilteredAirlines(origin, destination, sortColumn, sortType);
        log.info("In AirlineSearchController, end getAirlinesByCriteria: found airlines {}", result.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

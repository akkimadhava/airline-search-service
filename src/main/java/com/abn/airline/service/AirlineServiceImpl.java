package com.abn.airline.service;

import com.abn.airline.dto.AirlineSearchResponseDTO;
import com.abn.airline.entity.Airline;
import com.abn.airline.exception.InternalServerErrorException;
import com.abn.airline.exception.InvalidRequestException;
import com.abn.airline.exception.NoDataFoundException;
import com.abn.airline.repository.AirlineRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AirlineServiceImpl implements AirlineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirlineServiceImpl.class);
    private AirlineRepository airlineRepository;

    @Autowired
    public AirlineServiceImpl(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    /**
     * This method is used retrieve the airlines from backend
     * and does conversion from entity to DTO
     * input lift of AirlineSearchResponseDTO ,
     * Fields are
     * sortColumn : filed/property name to sort airline details on price/duration
     * sortType : type of sorting. optional to sort airline details with ASCE/DESC
     *
     * @return list of AirlineSearchResponseDTO
     */
    @Override
    public List<AirlineSearchResponseDTO> findFilteredAirlines(String origin, String destination, String sortColumn, String sortType) throws InvalidRequestException, NoDataFoundException {
        log.info("In AirlineServiceImpl, start findFilteredAirlines for origin:{} , destination {}", origin, destination);
        List<AirlineSearchResponseDTO> sortedResult = new ArrayList<>();
        try {
            final List<Airline> listOfAirlines = airlineRepository.findByOriginAndDestination(origin, destination);
            List<AirlineSearchResponseDTO> result = convertEntityToDTO(listOfAirlines);
            if (!ObjectUtils.isEmpty(result)) {
                if (Objects.isNull(sortColumn) || sortColumn.equalsIgnoreCase("fare") && (sortType.equalsIgnoreCase("asc")
                        || Objects.isNull(sortType))) {
                    sortedResult = result.stream().sorted(Comparator.comparingDouble(AirlineSearchResponseDTO::getFare)).toList();
                } else if (sortColumn.equalsIgnoreCase("fare") && sortType.equalsIgnoreCase("desc")) {
                    sortedResult = result.stream().sorted(Comparator.comparingDouble(AirlineSearchResponseDTO::getFare).reversed()).toList();
                } else if (sortColumn.equalsIgnoreCase("duration") && Objects.isNull(sortType)) {
                    sortedResult = result.stream().sorted(Comparator.comparingLong(AirlineServiceImpl::getAirlineDuration)).toList();
                } else if (sortColumn.equalsIgnoreCase("duration") && sortType.equalsIgnoreCase("desc")) {
                    sortedResult = result.stream().sorted(Comparator.comparingLong(AirlineServiceImpl::getAirlineDuration).reversed()).toList();
                } else {
                    throw new InvalidRequestException("Invalid/Bad request.");
                }
            } else {
                throw new NoDataFoundException("No Airline found.");
            }
        } catch (InvalidRequestException e) {
            log.error("Invalid/Bad request.");
            throw new InvalidRequestException("Invalid/Bad request.");
        } catch (NoDataFoundException e) {
            log.error("No Airline found.");
            throw new NoDataFoundException("No Airline found.");
        } catch (Exception e) {
            log.error("Exception occurred.");
            throw new InternalServerErrorException("Internal Server Error Occurred.");
        }
        log.info("In AirlineServiceImpl, call END findFilteredAirlines & found origin:{}", sortedResult.size());
        return sortedResult;
    }

    /**
     * this method converts list of airline entities to list of DTO objects
     * input lift of Airline
     *
     * @return list of AirlineSearchResponseDTO
     */
    private static List<AirlineSearchResponseDTO> convertEntityToDTO(List<Airline> airlineList) {
        if (ObjectUtils.isEmpty(airlineList)) return null;
        return airlineList.stream().map(data -> new AirlineSearchResponseDTO(data.getAirlineNumber(), data.getOrigin(), data.getDestination(), data.getDepartureTime(), data.getArrivalTime(), data.getFare())).toList();
    }

    /**
     * this method returns airline travel duration
     * input lift of AirlineSearchResponseDTO ,
     * Fields are
     * arrivalTime : airline start time
     * departureTime : airline sotp time
     *
     * @param airline
     * @return long duration - difference between arrival and departure time
     */
    private static Long getAirlineDuration(AirlineSearchResponseDTO airline) {
        return Duration.between(airline.getArrivalTime(), airline.getDepartureTime()).toHours();
    }
}

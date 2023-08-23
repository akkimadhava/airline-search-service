package com.abn.airline.service;

import com.abn.airline.dto.AirlineSearchResponseDTO;
import com.abn.airline.entity.Airline;
import com.abn.airline.exception.InternalServerErrorException;
import com.abn.airline.exception.InvalidRequestException;
import com.abn.airline.exception.NoDataFoundException;
import com.abn.airline.repository.AirlineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AirlineServiceTest {
    @Mock
    private AirlineRepository airlineRepository;
    @InjectMocks
    private AirlineServiceImpl airlineService;
    private static final String airlineOrigin = "AMS";
    private static final String airlineDestination = "BLR";

    @Test
    void testGetAirlineByOriginAndDestination() {
        Mockito.when(airlineRepository.findByOriginAndDestination(any(), any())).thenReturn(getMockedFilteredData(airlineOrigin, airlineDestination));
        List<AirlineSearchResponseDTO> listOfAirlines = airlineService.findFilteredAirlines(airlineOrigin, airlineDestination, null, null);
        assertEquals(3, listOfAirlines.size());
        verify(this.airlineRepository).findByOriginAndDestination(any(), any());
    }

    @Test
    void testGetAirlineSortByFare() {
        Mockito.when(airlineRepository.findByOriginAndDestination(any(), any())).thenReturn(getMockedFilteredData(airlineOrigin, airlineDestination));
        List<AirlineSearchResponseDTO> listOfAirlines = airlineService.findFilteredAirlines(airlineOrigin, airlineDestination, "fare", "desc");
        assertEquals(3, listOfAirlines.size());
        assertEquals(850.00, listOfAirlines.get(0).getFare());
        verify(this.airlineRepository).findByOriginAndDestination(any(), any());
    }

    @Test
    void testGetAirlineByDuration() {
        Mockito.when(airlineRepository.findByOriginAndDestination(any(), any())).thenReturn(getMockedFilteredData(airlineOrigin, airlineDestination));
        List<AirlineSearchResponseDTO> listOfAirlines = airlineService.findFilteredAirlines(airlineOrigin, airlineDestination, "duration", "desc");
        assertEquals(3, listOfAirlines.size());
        assertEquals(800.00, listOfAirlines.get(0).getFare());
        verify(this.airlineRepository).findByOriginAndDestination(any(), any());
    }

    @Test
    void testNoDataFoundException() {
        Mockito.when(airlineRepository.findByOriginAndDestination(any(), any())).thenReturn(null);
        Throwable thrown = assertThrows(NoDataFoundException.class, () -> airlineService.findFilteredAirlines(any(), any(), null, null));
        assertEquals("No Airline found.", thrown.getMessage());
    }

    @Test
    void testInvalidRequestException() {
        Mockito.when(airlineRepository.findByOriginAndDestination(any(), any())).thenReturn(getMockedFilteredData(airlineOrigin, airlineDestination));
        Throwable thrown = assertThrows(InvalidRequestException.class, () -> airlineService.findFilteredAirlines(any(), any(), "origin", "asc"));
        assertEquals("Invalid/Bad request.", thrown.getMessage());
    }

    @Test
    void testInternalServerException() {
        Mockito.when(airlineRepository.findByOriginAndDestination(any(), any())).thenReturn(getMockedEntityDataWithNullTime());
        Throwable thrown = assertThrows(InternalServerErrorException.class, () -> airlineService.findFilteredAirlines(any(), any(), "duration", "desc"));
        assertEquals("Internal Server Error Occurred.", thrown.getMessage());
    }

    public List<Airline> getMockedFilteredData(String origin, String destination) {
        return getMockedEntityData().stream().filter(x -> (origin.equalsIgnoreCase(x.getOrigin()) && destination.equalsIgnoreCase(x.getDestination()))).toList();
    }

    public List<Airline> getMockedEntityData() {
        return Arrays.asList(new Airline(1L, "C101", "AMS", "BLR", LocalTime.of(11, 00), LocalTime.of(17, 00), 800.00),
                new Airline(2L, "A101", "AMS", "DEL", LocalTime.of(13, 00), LocalTime.of(18, 00), 850.00),
                new Airline(1L, "C103", "AMS", "BLR", LocalTime.of(10, 30), LocalTime.of(17, 00), 730.00),
                new Airline(2L, "C102", "AMS", "BLR", LocalTime.of(12, 00), LocalTime.of(18, 00), 850.00));
    }

    public List<Airline> getMockedEntityDataWithNullTime() {
        return Arrays.asList(new Airline(1L, "C101", "AMS", "BLR", null, null, 800.00),
                new Airline(2L, "C102", "AMS", "BLR", null, null, 850.00));
    }
}

package com.abn.airline.controller;

import com.abn.airline.api.AirlineSearchController;
import com.abn.airline.dto.AirlineSearchResponseDTO;
import com.abn.airline.exception.InvalidRequestException;
import com.abn.airline.service.AirlineServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirlineSearchControllerTest {

    @InjectMocks
    AirlineSearchController controller;
    @Mock
    AirlineServiceImpl airlineService;

    @BeforeEach
    public void initEach() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testGetAirlinesByOriginAndDestination() throws Exception {
        when(airlineService.findFilteredAirlines(any(), any(), any(), any())).thenReturn(getMockedData());
        ResponseEntity<List<AirlineSearchResponseDTO>> responseEntity = controller.getAirlinesByCriteria("AMS", "BLR", "fare", null);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void testGetAirlineWithBadRequest() throws Exception {
        when(airlineService.findFilteredAirlines(any(), any(), any(), any())).thenThrow(new InvalidRequestException("Invalid/Bad request."));
        try {
            ResponseEntity<List<AirlineSearchResponseDTO>> responseEntity = controller.getAirlinesByCriteria("AMS", "BLR", "fare", null);
            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        } catch (InvalidRequestException e) {
            Assertions.assertEquals("Invalid/Bad request.", e.getMessage());
        }
    }

    public List<AirlineSearchResponseDTO> getMockedData() {
        return Arrays.asList(new AirlineSearchResponseDTO("A102", "AMS", "BLR", LocalTime.now(), LocalTime.now(), 850.00),
                new AirlineSearchResponseDTO("A101", "AMS", "BLR", LocalTime.now(), LocalTime.now(), 800.00)
        );
    }
}

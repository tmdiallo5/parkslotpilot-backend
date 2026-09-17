package tech.mavi.ms_parking.reservations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tech.mavi.ms_parking.enums.ReservationStatus;
import tech.mavi.ms_parking.profiles.ProfileController;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ReservationService reservationService;

    @BeforeEach
    void setUp() {
        ReservationResponseDto reservationResponseDto = ReservationResponseDto.builder()
                .reservationStatus(ReservationStatus.CONFIRMED)
                .build();
        when(reservationService.createReservation(any(ReservationRequestDto.class)))
                .thenReturn(reservationResponseDto);

        ReservationDTO reservationDTO = ReservationDTO.builder()
                .reservationStatus(ReservationStatus.CONFIRMED)
                .build();
        when(reservationService.myReservations()).thenReturn(List.of(reservationDTO));
    }



    @Test
    void createReservation() throws Exception {
     ReservationRequestDto requestDto = ReservationRequestDto.builder()
             .spotId(1)
             .build();
        String reservationAsString =
                new ObjectMapper().writeValueAsString(requestDto);
        this.mockMvc.perform(post("/reservation")
                .content(reservationAsString)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isCreated());
    }

    @Test
    void myReservations() throws Exception {
        this.mockMvc.perform(get("/my-reservations"))
                .andExpect(status().isOk())
                .andDo(print());

    }
}
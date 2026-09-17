package tech.mavi.ms_parking.reservations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.mavi.ms_parking.spots.AvailableSpotRequestDto;
import tech.mavi.ms_parking.spots.AvailableSpotResponseDto;

import tech.mavi.ms_parking.spots.SpotResponseDto;


import java.time.LocalDateTime;
import java.util.List;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@RequestMapping
@RestController
public class ReservationController {

    private final ReservationService reservationService;


    @PostMapping(path = "reservation", consumes = APPLICATION_JSON_VALUE )
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDto createReservation(@RequestBody ReservationRequestDto reservationDto) {
        return this.reservationService.createReservation(reservationDto);
    }

    @PostMapping(path = "available-spot", consumes = APPLICATION_JSON_VALUE)
    public List<AvailableSpotResponseDto> findAvailableSpot(@RequestBody AvailableSpotRequestDto availableSpotRequestDto) {
        return this.reservationService.findAvailableSpot(
                availableSpotRequestDto.addressId(),
                availableSpotRequestDto.startDateTime(),
                availableSpotRequestDto.endDateTime()
        );
    }

    @GetMapping(path = "my-reservations", produces = APPLICATION_JSON_VALUE)
    public List<ReservationDTO> myReservations(){
        return this.reservationService.myReservations();
    }
    @PatchMapping(path = "cancel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ReservationResponseDto cancelReservation(@PathVariable int id) {
        return this.reservationService.cancelReservation(id);
    }
    @PutMapping(path = "update/{id}" )
    public ReservationResponseDto reservationUpdate(@PathVariable int id, @RequestBody ReservationUpdateRequest reservationUpdateRequest) {
        return this.reservationService.reservationUpdate(id, reservationUpdateRequest);
    }
    @GetMapping(path = "available-spot/{parkingId}")
    public List<SpotResponseDto> availableSpotsByParking(
            @PathVariable int parkingId,
            @RequestParam LocalDateTime startDateTime,
            @RequestParam LocalDateTime endDateTime,
            @RequestParam int currentReservationId
            ){
        return this.reservationService.availableSpotsByParking(parkingId, startDateTime, endDateTime, currentReservationId);
    }

}

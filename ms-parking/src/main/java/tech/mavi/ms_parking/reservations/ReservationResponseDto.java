package tech.mavi.ms_parking.reservations;

import lombok.Builder;
import tech.mavi.ms_parking.enums.ReservationStatus;

import java.time.LocalDateTime;
@Builder
public record ReservationResponseDto(
        int id,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        ReservationStatus reservationStatus,
        int spotId

) {

}


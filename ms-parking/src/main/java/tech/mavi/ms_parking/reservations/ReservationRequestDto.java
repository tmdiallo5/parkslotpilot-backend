package tech.mavi.ms_parking.reservations;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record ReservationRequestDto(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Integer spotId

        ) {
}



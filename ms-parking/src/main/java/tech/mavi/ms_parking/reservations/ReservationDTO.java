package tech.mavi.ms_parking.reservations;

import lombok.Builder;
import tech.mavi.ms_parking.enums.ReservationStatus;
import tech.mavi.ms_parking.enums.SpotType;
import tech.mavi.ms_parking.shared.entities.address.Address;

import java.time.LocalDateTime;
@Builder
public record ReservationDTO(
        int id,
        int parkingId,
        String parkingName,
        Address address,
        int spotId,
        String spotNumber,
        SpotType spotType,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        ReservationStatus reservationStatus,
        LocalDateTime cancelledAt,
        LocalDateTime createdAt,
        String imageUrl


) {
}





package tech.mavi.ms_parking.reservations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.mavi.ms_parking.enums.ReservationStatus;
import tech.mavi.ms_parking.profiles.Profile;
import tech.mavi.ms_parking.security.service.SecurityService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;
    @Mock
    SecurityService securityService;
    @Mock
    ReservationRepository reservationRepository;
    ReservationStatus reservationStatus;
    @Mock
    Profile profile;
    @Mock
    ReservationMapper reservationMapper;

    @Test
    void shouldReturnMyReservations() {
        Profile profile = new Profile();
        profile.setId(1);

        Reservation reservation = new Reservation();
        reservation.setReservationStatus(ReservationStatus.CONFIRMED);
        reservation.setEndDateTime(LocalDateTime.now().plusDays(1));

        when(securityService.getCurrentUser()).thenReturn(profile);
        when(reservationRepository.findByProfileIdOrderByUpdatedAtDesc(profile.getId()))
                .thenReturn(List.of(reservation));
        List<ReservationDTO> result = reservationService.myReservations();

        assertEquals(1, result.size());
    }
}
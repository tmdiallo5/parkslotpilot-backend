package tech.mavi.ms_parking.reservations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tech.mavi.ms_parking.enums.ReservationStatus;
import tech.mavi.ms_parking.profiles.Profile;
import tech.mavi.ms_parking.profiles.ProfileRepository;
import tech.mavi.ms_parking.spots.Spot;
import tech.mavi.ms_parking.spots.SpotRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ReservationRepositoryTest {
    /*@SpringBootConfiguration
    @EnableAutoConfiguration
    @EntityScan(basePackages = "tech.mavi.ms_parking")
    @EnableJpaRepositories(basePackages = "tech.mavi.ms_parking")
    static class TestApplication {
    }*/

    private  LocalDateTime start;
    private  LocalDateTime end;
    private Integer spotID;
    private Integer profileID;

    @Autowired
    SpotRepository spotRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ProfileRepository profileRepository;

    @BeforeEach
    void setUp() {
        Spot spot = new Spot();
        spot = spotRepository.save(spot);
        spotID = spot.getId();

        Profile profile = Profile.builder().email("diallo@example.com").build();
        profileRepository.save(profile);
        profileID = profile.getId();



        start = LocalDateTime.now();
        end = start.plusDays(1);

        Reservation reservation = new Reservation();
        reservation.setSpot(spot);
        reservation.setProfile(profile);
        reservation.setReservationStatus(ReservationStatus.CONFIRMED);
        reservation.setStartDateTime(start);
        reservation.setEndDateTime(end);

        reservationRepository.save(reservation);



    }

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
    }

    @Test
    void findBySpotIdInAndReservationStatusAndStartDateTimeBeforeAndEndDateTimeAfter() {
      List<Reservation> reservations = reservationRepository.findBySpotIdInAndReservationStatusAndStartDateTimeBeforeAndEndDateTimeAfter(
                List.of(spotID),
                ReservationStatus.CONFIRMED,
                end,
                start
        );
        assertEquals(1, reservations.size());
    }

    @Test
    void findByProfileIdOrderByUpdatedAtDesc() {
        List<Reservation> reservations = reservationRepository.findByProfileIdOrderByUpdatedAtDesc(profileID);
        assertEquals(1, reservations.size());
    }
}


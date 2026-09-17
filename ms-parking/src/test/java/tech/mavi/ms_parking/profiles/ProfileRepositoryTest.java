package tech.mavi.ms_parking.profiles;

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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProfileRepositoryTest {

  /*  @SpringBootConfiguration
    @EnableAutoConfiguration
    @EntityScan(basePackages = "tech.mavi.ms_parking")
    @EnableJpaRepositories(basePackages = "tech.mavi.ms_parking")
    static class TestApplication {
    }*/

    @Autowired
    private ProfileRepository profileRepository;

    @BeforeEach
    void setUp(){
        Profile profile1 = Profile.builder().email("diallo@example.com").build();
        Profile profile2 = Profile.builder().email("mavi@example.com").build();

        profileRepository.saveAll(List.of(profile1, profile2));
    }

    @AfterEach
    void tearDown(){
        profileRepository.deleteAll();
    }

    @Test
    void shouldReturnListProfiles() {

      List<Profile> listProfile = profileRepository.findAll();
       assertEquals(2, listProfile.size());

    }

    @Test
    void shouldReturnProfileByEmail() {
       Optional<Profile> profileEmail = profileRepository.findByEmail("diallo@example.com");
       assertTrue(profileEmail.isPresent());
    }

    @Test
    void shouldReturnEmptyProfileByEmail() {
        Optional<Profile> profileEmail = profileRepository.findByEmail("alice@example.com");
        assertTrue(profileEmail.isEmpty());
    }
}
package tech.mavi.ms_parking.profiles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    ProfileRepository profileRepository;
    @Mock
    ProfileMapper profileMapper;

    @InjectMocks
    ProfileService profileService;

    @Test
    void shouldReturnEmptyProfile() {
        Set<ProfileDTO> profiles = this.profileService.getAllprofiles();

        assertTrue(profiles.isEmpty());
    }

    @Test
    void shouldReturnProfiles(){
        Profile profile = new Profile();
        ProfileDTO profileDTO = new ProfileDTO("Thierno", "Diallo", "thierno@test.de", "123456");

        when(profileRepository.findAll())
                .thenReturn(List.of(profile));
        when(profileMapper.entityToDto(profile))
                .thenReturn(profileDTO);

       Set<ProfileDTO> result = profileService.getAllprofiles();

        assertEquals(1, result.size());
    }


}
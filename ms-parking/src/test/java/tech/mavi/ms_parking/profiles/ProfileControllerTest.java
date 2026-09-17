package tech.mavi.ms_parking.profiles;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProfileService profileService;

    @BeforeEach
    void setUp() {
        ProfileDTO profile1 = ProfileDTO.builder().email("diallo@example.com").build();
        ProfileDTO profile2 = ProfileDTO.builder().email("mavi@example.com").build();
        when(profileService.getAllprofiles()).thenReturn((Set.of(profile1, profile2)));
    }



    @Test
    void getAllProfiles() throws Exception {
        this.mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("mavi@example.com")));
    }
}
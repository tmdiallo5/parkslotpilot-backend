package tech.mavi.ms_parking.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tech.mavi.ms_parking.profiles.ProfileController;
import tech.mavi.ms_parking.profiles.ProfileDTO;
import tech.mavi.ms_parking.profiles.ProfileService;
import tech.mavi.ms_parking.security.token.JwtService;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthentificationController.class)
class AuthentificationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AuthentificationService authentificationService;

    @MockitoBean
    AuthenticationManager authenticationManager;

    @MockitoBean
    JwtService jwtService;



    @Test
    void create() throws Exception {
        ProfileDTO profile1 = ProfileDTO.builder().email("diallo@example.com").build();
        String profileAsString = new ObjectMapper().writeValueAsString(profile1);
        this.mockMvc.perform(post("/sign-up")
                .content(profileAsString)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isCreated());

    }
}
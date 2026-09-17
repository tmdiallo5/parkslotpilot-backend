package tech.mavi.ms_parking.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ValidationsServiceTest {

    @InjectMocks
    private ValidationsService validationsService;

    @Test
    void shouldTestThatEmailIsvalidate() {
        String email = "test@email.com";
        validationsService.validateEmail(email);
    }
    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        String email = null;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> validationsService.validateEmail(email));
        assertEquals("Email is null or empty", exception.getMessage());
    }
}
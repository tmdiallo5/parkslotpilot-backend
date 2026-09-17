package tech.mavi.ms_parking.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ValidationsService {

    public void validateEmail(String email) {
        if(email == null || email.isBlank()) {
            throw new RuntimeException("Email is null or empty");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new RuntimeException("Invalid email format");
        }
        if(email.startsWith("@") || email.endsWith("@")){
            throw new RuntimeException("Invalid email format");

        }
    }
}

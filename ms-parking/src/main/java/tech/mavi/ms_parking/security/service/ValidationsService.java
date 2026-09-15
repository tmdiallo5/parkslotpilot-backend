package tech.mavi.ms_parking.security.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ValidationsService {

    public void validateEmail(String email) {
        if(email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is null or empty");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format");
        }
        if(email.startsWith("@") || email.endsWith("@")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format");

        }
    }
}

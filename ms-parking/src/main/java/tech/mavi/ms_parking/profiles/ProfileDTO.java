package tech.mavi.ms_parking.profiles;

import lombok.Builder;

@Builder
public record ProfileDTO(
         String firstName,
         String lastName,
         String email,
         String password
) {
}

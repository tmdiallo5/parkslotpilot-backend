package tech.mavi.ms_parking.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tech.mavi.ms_parking.authentication.AuthentificationService;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class ApplicationSecurity {

    private RsaKeys rsaKeys;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthentificationService authentificationService;


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(this.authentificationService);
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
        return daoAuthenticationProvider;
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            return
                    httpSecurity
                            .cors(Customizer.withDefaults())
                            .csrf(AbstractHttpConfigurer::disable)
                            .headers(AbstractHttpConfigurer::disable)
                            .authorizeHttpRequests(
                                    customizer ->
                                            customizer
                                                    .requestMatchers(HttpMethod.POST,"/sign-up").permitAll()
                                                    .requestMatchers(HttpMethod.POST,"/sign-in").permitAll()
                                                    .requestMatchers(HttpMethod.POST,"/activate").permitAll()
                                                    .requestMatchers(HttpMethod.POST,"/error").permitAll()
                                                    .requestMatchers(HttpMethod.GET,"/search-address").permitAll()
                                                    .requestMatchers(HttpMethod.POST,"/available-spot").permitAll()
                                                    .requestMatchers(HttpMethod.POST, "/prompts/define").permitAll()
                                                    .requestMatchers(HttpMethod.POST, "/prompts/roadmap").permitAll()
                                                    .requestMatchers(HttpMethod.POST, "/prompts/chat").permitAll()
                                                    .anyRequest().authenticated()
                            )
                            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                            .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
                            .build();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) {
       AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(this.authenticationProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.rsaKeys.publicKey()).build();
    }
    @Bean
    JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey())
                .privateKey(rsaKeys.privateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }
}

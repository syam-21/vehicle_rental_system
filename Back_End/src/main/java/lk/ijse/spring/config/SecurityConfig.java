package lk.ijse.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(
                "/",                     // allow root
                "/**",                  // allow everything (frontend HTML, CSS, JS)
                "/api/v1/auth/**",      // allow login, register
                "/api/v1/vehicles/**",  // allow vehicle list
                "/api/v1/bookings/**"   // allow booking API
            ).permitAll()
            .anyRequest().authenticated();

        return http.build();
    }
}

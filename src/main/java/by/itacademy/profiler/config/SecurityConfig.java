package by.itacademy.profiler.config;

import by.itacademy.profiler.security.jwt.JwtTokenFilterConfigurer;
import by.itacademy.profiler.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/actuator/**",
            "/api/v1/auth/login",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**"
    };
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtTokenFilterConfigurer(jwtTokenProvider))
                .and()
                .httpBasic().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}



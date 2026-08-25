package ru.mariiic.impl.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.mariiic.securitystarter.configurer.JwtConfigurer;
import ru.mariiic.securitystarter.customizer.SecurityCustomizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] PERMIT_ALL = {"/api/auth/**", };
    private static final String[] AUTHENTICATED = {"/api/auth/password"};

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtConfigurer jwtConfigurer) throws Exception {
//        http
//                .with(jwtConfigurer, Customizer.withDefaults())
//                .authorizeHttpRequests(authorizeRequests
//                        -> authorizeRequests
//                        .requestMatchers(PERMIT_ALL).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .logout(logout -> logout.logoutUrl("/api/auth/logout"));
//        return http.build();
//    }

    @Bean
    public SecurityCustomizer securityCustomizer() {
        return auth ->
                auth.requestMatchers(PERMIT_ALL).permitAll();
    }
}

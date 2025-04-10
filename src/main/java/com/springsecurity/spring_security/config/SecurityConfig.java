package com.springsecurity.spring_security.config;

import com.springsecurity.spring_security.service.JpaUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JpaUserDetailService jpaUserDetailService;
    public  SecurityConfig(JpaUserDetailService jpaUserDetailService){
        this.jpaUserDetailService = jpaUserDetailService;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers("/h2-console/**").permitAll()
                                        .requestMatchers("/api/posts").permitAll()
                                        .anyRequest().authenticated())
                .userDetailsService(jpaUserDetailService)
               .headers(headers -> {
                   headers.frameOptions().sameOrigin();
               })
               .httpBasic(Customizer.withDefaults())
                .build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}

package com.hackaboss.pruebatec4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/agencia/vuelos").permitAll()
                .requestMatchers("/agencia/vuelos/buscar").permitAll()
                .requestMatchers("/agencia/reserva-vuelo/nuevo").permitAll()
                .requestMatchers("/agencia/hoteles").permitAll()
                .requestMatchers("/agencia/hoteles/buscar").permitAll()
                .requestMatchers("/agencia/hoteles/habitaciones").permitAll()
                .requestMatchers("/agencia/reserva-hotel/nuevo").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .httpBasic()
                .and()
                .build();


    }


}

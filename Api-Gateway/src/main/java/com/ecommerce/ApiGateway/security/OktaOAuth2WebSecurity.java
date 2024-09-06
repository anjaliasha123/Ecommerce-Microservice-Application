package com.ecommerce.ApiGateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class OktaOAuth2WebSecurity {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {

//        http
//                .authorizeHttpRequests(
//                        authorize -> authorize.anyRequest().authenticated()
//                )
//                .oauth2Login(Customizer.withDefaults())  // enables OAuth2 login with default settings
//                .oauth2ResourceServer(
//                        oauth2 -> oauth2.jwt(Customizer.withDefaults()) // enables JWT resource server support with default settings
//                );


        return http.authorizeExchange(
                exchanges -> exchanges
                        .anyExchange().authenticated()
        ).oauth2Login(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                .build();
    }
}

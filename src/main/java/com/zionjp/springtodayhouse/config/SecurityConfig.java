package com.zionjp.springtodayhouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf((csrfConfig) -> {
            csrfConfig.disable();
        })
        .headers((headerConfig)-> {
            headerConfig.frameOptions(frameOptionsConfig ->
                    frameOptionsConfig.disable());
        })
        .authorizeHttpRequests((authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers("/**").permitAll();
        }));
        return httpSecurity.build();
    }
}

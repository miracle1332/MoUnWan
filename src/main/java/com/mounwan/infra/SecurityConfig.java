package com.mounwan.infra;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                .requestMatchers("/", "/login", "sign-up", "checked-email", "check-email-token",
                        "/email-login", "/login-link").permitAll()
                .requestMatchers(HttpMethod.GET, "/profile/*").permitAll() //get요청만 허용
                .anyRequest().authenticated()); //나머지 요청은 로그인해야 가능

        http.formLogin((formLogin) ->
                        formLogin
                .loginPage("/login").permitAll());

        http.logout((logout) -> logout
                .logoutSuccessUrl("/"));



        return  http.build();
    }

}

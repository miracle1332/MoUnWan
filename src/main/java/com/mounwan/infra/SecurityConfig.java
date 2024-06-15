package com.mounwan.infra;


import com.mounwan.moudules.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final AccountService accountService;
    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requestMatcherRegistry->requestMatcherRegistry
                .requestMatchers("/", "/login", "sign-up", "checked-email", "check-email-token",
                        "/email-login", "/login-link").permitAll()
                .requestMatchers("/node_moudules/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/profile/*").permitAll() //get요청만 허용
                .anyRequest().authenticated()); //나머지 요청은 로그인해야 가능

        http.formLogin((formLogin) ->
                        formLogin
                .loginPage("/login").permitAll());

        http.logout((logout) -> logout
                .logoutSuccessUrl("/"));

        http.rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer
                .userDetailsService(accountService)
                .tokenRepository(tokenRepository()));

        return  http.build();
    }


    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }



}

package com.example.flora_shop.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.example.flora_shop.domain.Role;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        (csrfConfig) -> csrfConfig.disable()
                )
                .headers(
                        (headerConfig) -> headerConfig.frameOptions(
                                frameOptionsConfig -> frameOptionsConfig.disable()
                        )
                )
                .authorizeHttpRequests((authorizeRequest) -> authorizeRequest
                        .requestMatchers("/comments/save").hasRole(String.valueOf(Role.USER)) // 기존 코드 : Role.USER.name(), String.valueOf(Role.USER)
                        .requestMatchers("/login", "/register", "/login", "/upload", "/mypage", "/cart").permitAll()
                        .requestMatchers("/oauth2/authorization/google").permitAll()
                        .requestMatchers("/", "/css/**", "images/**", "/js/**", "/login", "/logout/*", "/posts/**", "/comments/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout( // 로그아웃 성공 시 / 주소로 이동
                        (logoutConfig) -> logoutConfig.logoutSuccessUrl("/")
                )

                // OAuth2 로그인 기능에 대한 여러 설정
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/oauth2/authorization/google")  // 로그인 페이지 설정
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(customOAuth2UserService)
                                )
                );

        return http.build();
    }
}

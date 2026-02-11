package com.third.gen_office.config;

import com.third.gen_office.infrastructure.authentication.AbsoluteSessionTimeoutFilter;
import com.third.gen_office.infrastructure.authentication.CsrfDebugLoggingFilter;
import com.third.gen_office.infrastructure.authentication.RequestLoggingFilter;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(
                        "/api/i18n",
                        "/api/auth/login",
                        "/api/auth/tmp-login",
                        "/api/auth/me",
                        "/api/auth/csrf"
                ).permitAll()
                .anyRequest().authenticated())
            .addFilterBefore(requestLoggingFilter(), CsrfFilter.class)
            .addFilterBefore(csrfDebugLoggingFilter(), CsrfFilter.class)
            .addFilterBefore(absoluteSessionTimeoutFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        HttpSecurity http,
        UserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder
    ) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }

    @Bean
    public AbsoluteSessionTimeoutFilter absoluteSessionTimeoutFilter() {
        return new AbsoluteSessionTimeoutFilter(Duration.ofHours(8));
    }

    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }

    @Bean
    public CsrfDebugLoggingFilter csrfDebugLoggingFilter() {
        return new CsrfDebugLoggingFilter();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}

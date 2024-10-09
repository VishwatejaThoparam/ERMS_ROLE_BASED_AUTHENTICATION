package com.erms.ERMS_Application.Authentication.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/auth/logout",
            "/api/users/**"
    };

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final LogoutHandler logoutHandler;

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthFilter, LogoutHandler logoutHandler) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
                .authorizeHttpRequests(req -> req
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        // Organization access control
                        .requestMatchers("/api/auth/organize/**").permitAll()
                        // Admin access control
                        .requestMatchers("/api/v1/admin/**").hasAnyRole("ORGANIZATION", "ADMIN")
                        .requestMatchers(GET, "/api/v1/admin/**").hasAnyAuthority("ORGANIZATION_READ", "ADMIN_READ")
                        .requestMatchers(POST, "/api/v1/admin/**").hasAnyAuthority("ORGANIZATION_CREATE", "ADMIN_CREATE")
                        .requestMatchers(PUT, "/api/v1/admin/**").hasAnyAuthority("ORGANIZATION_UPDATE", "ADMIN_UPDATE")
                        .requestMatchers(DELETE, "/api/v1/admin/**").hasAnyAuthority("ORGANIZATION_DELETE", "ADMIN_DELETE")
                        // Manager access control
                        .requestMatchers("/api/v1/management/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority("ADMIN_READ", "MANAGER_READ")
                        .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority("ADMIN_CREATE", "MANAGER_CREATE")
                        .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority("ADMIN_UPDATE", "MANAGER_UPDATE")
                        .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority("ADMIN_DELETE", "MANAGER_DELETE")
                        // Sale Manager and others
                        .requestMatchers("/api/v1/saleManagement/**").hasAnyRole("ADMIN", "MANAGER", "SALE_MANAGER")
                        .requestMatchers(GET, "/api/v1/saleManagement/**").hasAnyAuthority("MANAGER_READ", "ADMIN_READ", "SALE_MANAGER_READ")
                        .requestMatchers(POST, "/api/v1/saleManagement/**").hasAnyAuthority("MANAGER_CREATE", "ADMIN_CREATE", "SALE_MANAGER_CREATE")
                        .requestMatchers(PUT, "/api/v1/saleManagement/**").hasAnyAuthority("MANAGER_UPDATE", "ADMIN_UPDATE", "SALE_MANAGER_UPDATE")
                        .requestMatchers(DELETE, "/api/v1/saleManagement/**").hasAnyAuthority("MANAGER_DELETE", "ADMIN_DELETE", "SALE_MANAGER_DELETE")
                        // Technician access control
                        .requestMatchers("/api/v1/technician/**").hasAnyRole("ADMIN", "MANAGER", "SALE_MANAGER", "TECHNICIAN")
                        .requestMatchers(GET, "/api/v1/technician/**").hasAnyAuthority("TECHNICIAN_READ", "SALE_MANAGER_READ", "MANAGER_READ", "ADMIN_READ")
                        .requestMatchers(POST, "/api/v1/technician/**").hasAnyAuthority("TECHNICIAN_CREATE", "SALE_MANAGER_CREATE", "MANAGER_CREATE", "ADMIN_CREATE")
                        .requestMatchers(PUT, "/api/v1/technician/**").hasAnyAuthority("TECHNICIAN_UPDATE", "SALE_MANAGER_UPDATE", "MANAGER_UPDATE", "ADMIN_UPDATE")
                        .requestMatchers(DELETE, "/api/v1/technician/**").hasAnyAuthority("TECHNICIAN_DELETE", "SALE_MANAGER_DELETE", "MANAGER_DELETE", "ADMIN_DELETE")
                        // Sales access control
                        .requestMatchers("/api/v1/sales/**").hasAnyRole("ADMIN", "MANAGER", "SALE_MANAGER", "SALES")
                        .requestMatchers(GET, "/api/v1/sales/**").hasAnyAuthority("SALES_READ", "SALE_MANAGER_READ", "MANAGER_READ", "ADMIN_READ")
                        .requestMatchers(POST, "/api/v1/sales/**").hasAnyAuthority("SALES_CREATE", "SALE_MANAGER_CREATE", "MANAGER_CREATE", "ADMIN_CREATE")
                        .requestMatchers(PUT, "/api/v1/sales/**").hasAnyAuthority("SALES_UPDATE", "SALE_MANAGER_UPDATE", "MANAGER_UPDATE", "ADMIN_UPDATE")
                        .requestMatchers(DELETE, "/api/v1/sales/**").hasAnyAuthority("SALES_DELETE", "SALE_MANAGER_DELETE", "MANAGER_DELETE", "ADMIN_DELETE")
                        // Quotation
                        .requestMatchers("api/sale/**").permitAll()
                        .requestMatchers("api/itemstable/**").permitAll()
                        .requestMatchers("api/bankdetails/**").permitAll()
                        .requestMatchers("api/form/**").permitAll()
                        .requestMatchers("api/addparty/**").permitAll()
                        .requestMatchers("api/addlist/**").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://192.168..", "http://localhost:3000","http://localhost:3001")); // Allow your frontend origin
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization")); // Allow all headers
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Allow necessary HTTP methods

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Register the CORS configuration for all paths
        return source;
    }
}

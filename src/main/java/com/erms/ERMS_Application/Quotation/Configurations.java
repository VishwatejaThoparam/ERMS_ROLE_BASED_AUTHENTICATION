package com.erms.ERMS_Application.Quotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class Configurations implements WebMvcConfigurer {
	
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://192.168..", "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(true);
    }

    // Route forwarding for React refresh issue
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{part1:[a-zA-Z0-9-_]+}")
                .setViewName("forward:/index.html");
        registry.addViewController("/{part1:[a-zA-Z0-9-_]+}/{part2:[a-zA-Z0-9-_]+}")
                .setViewName("forward:/index.html");
        registry.addViewController("/{part1:[a-zA-Z0-9-_]+}/{part2:[a-zA-Z0-9-_]+}/{part3:[a-zA-Z0-9-_]+}")
                .setViewName("forward:/index.html");
    }

}

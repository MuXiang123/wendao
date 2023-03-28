package com.example.wendao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/2 10:10
 * @version: 1.0
 */
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                        .allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }
}

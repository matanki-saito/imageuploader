package com.popush.imageuploader.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/manager/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOrigins("https://triela.ml:6443");
    }
}

package com.uceva.fitmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Remover el prefijo /api de todos los controladores automáticamente
        configurer.addPathPrefix("/v1", c -> c.getPackage().getName().contains("controller"));
    }
}

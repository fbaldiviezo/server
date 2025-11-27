package com.proyecto.backend_2.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica la configuración a todos los endpoints bajo /
                .allowedOrigins("*") // Permite peticiones desde la URL de tu app de Angular
                .allowedMethods("*") // Permite los métodos HTTP comunes
                .allowedHeaders("*") // Permite todos los encabezados
                .allowCredentials(false); // Permite el envío de cookies y credenciales
    }
}

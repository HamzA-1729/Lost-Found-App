package com.lostandfound.config; // This configuration class handles CORS mappings and resource handling for file uploads.

import org.springframework.context.annotation.Configuration; // Importing the Configuration annotation for Spring
import org.springframework.web.servlet.config.annotation.CorsRegistry; // Importing CorsRegistry for handling CORS configurations
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry; // Importing ResourceHandlerRegistry to map resource paths
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // Importing WebMvcConfigurer to implement web-related configurations

@Configuration // Marks this class as a configuration class for Spring
public class WebConfig implements WebMvcConfigurer { // Implements WebMvcConfigurer to customize the Spring MVC
                                                     // configuration

    @Override // Overriding the method to configure CORS mappings
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS requests from the frontend (React app running on localhost:3000)
        registry.addMapping("/api/**").allowedOrigins("http://localhost:3000"); // Allows CORS for the /api/** endpoints
                                                                                // from the specified origin
    }

    @Override // Overriding the method to configure resource handlers
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**") // Mapping the /uploads/** path to the resource handler
                .addResourceLocations("file:uploads/"); // Specifies the location for serving files, pointing to the
                                                        // uploads directory
    }
}

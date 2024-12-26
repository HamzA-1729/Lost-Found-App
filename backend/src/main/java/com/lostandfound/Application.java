package com.lostandfound; // This is the entry point of the Spring Boot application.

import org.springframework.boot.SpringApplication; // Importing the SpringApplication class to launch the application.
import org.springframework.boot.autoconfigure.SpringBootApplication; // Importing the SpringBootApplication annotation to enable auto-configuration.

@SpringBootApplication // This annotation enables Spring Boot's auto-configuration, component scanning,
                       // and configuration properties.
public class Application { // The main application class to run the Spring Boot application.

    public static void main(String[] args) { // Main method, which serves as the entry point of the application.
        SpringApplication.run(Application.class, args); // Launches the Spring Boot application.
    }
}

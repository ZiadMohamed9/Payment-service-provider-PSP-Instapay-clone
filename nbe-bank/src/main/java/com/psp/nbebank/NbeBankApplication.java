package com.psp.nbebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the NbeBank application.
 * This is the entry point of the Spring Boot application.
 */
@SpringBootApplication
public class NbeBankApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(NbeBankApplication.class, args);
    }

}
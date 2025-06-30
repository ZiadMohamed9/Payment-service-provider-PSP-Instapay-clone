package com.psp.instapay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the Instapay application.
 * This is the entry point of the Spring Boot application.
 * It also enables Feign clients for making HTTP requests to external services.
 */
@SpringBootApplication
@EnableFeignClients
public class InstapayApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(InstapayApplication.class, args);
    }

}
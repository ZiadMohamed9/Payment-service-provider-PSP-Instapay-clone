package com.psp.cibbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the CIB Bank service.
 * This service simulates the CIB Bank's API for the Payment Service Provider system.
 * It provides endpoints for account management, card operations, and transaction processing.
 */
@SpringBootApplication
public class CibBankApplication {

	/**
	 * The main method that starts the CIB Bank service.
	 * 
	 * @param args Command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(CibBankApplication.class, args);
	}

}

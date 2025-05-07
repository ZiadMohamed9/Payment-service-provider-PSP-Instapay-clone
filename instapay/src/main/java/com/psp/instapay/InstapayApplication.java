package com.psp.instapay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InstapayApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstapayApplication.class, args);
	}

}

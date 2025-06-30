package com.psp.nbebank.controller;

import com.psp.nbebank.model.dto.response.ApiResponse;
import com.psp.nbebank.model.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing customer-related operations.
 * Provides endpoints to retrieve customer information.
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    /**
     * Retrieves customer information based on the provided phone number.
     *
     * @param phoneNumber the phone number of the customer to look up
     * @return a ResponseEntity containing an ApiResponse with the customer existence status
     */
    @PostMapping("/phone")
    public ResponseEntity<ApiResponse> getCustomerByPhoneNumber(@RequestBody String phoneNumber) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Success!")
                        .data(customerService.isCustomerExists(phoneNumber))
                        .build()
        );
    }
}
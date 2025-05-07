package com.psp.nbebank.controller;

import com.psp.nbebank.model.dto.response.ApiResponse;
import com.psp.nbebank.model.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<ApiResponse> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Success!")
                        .data(customerService.isCustomerExists(phoneNumber))
                        .build()
        );
    }
}

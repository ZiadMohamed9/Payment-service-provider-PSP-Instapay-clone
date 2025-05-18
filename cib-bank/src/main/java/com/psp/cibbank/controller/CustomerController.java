package com.psp.cibbank.controller;

import com.psp.cibbank.model.dto.response.ApiResponse;
import com.psp.cibbank.model.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

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

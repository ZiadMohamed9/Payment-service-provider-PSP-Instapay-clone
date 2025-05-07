package com.psp.nbebank.controller;

import com.psp.nbebank.model.dto.response.ApiResponse;
import com.psp.nbebank.model.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{accountNumber}/balance")
    public ApiResponse getBalance(@PathVariable String accountNumber) {
        try {
            Double balance = accountService.getBalance(accountNumber);
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Balance retrieved successfully")
                    .data(balance)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Error retrieving balance: " + e.getMessage())
                    .build();
        }
    }
}

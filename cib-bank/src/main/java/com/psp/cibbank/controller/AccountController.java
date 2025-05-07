package com.psp.cibbank.controller;

import com.psp.cibbank.model.service.AccountService;
import com.psp.cibbank.model.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

package com.psp.instapay.controller;

import com.psp.instapay.model.dto.request.AccountDetailsRequest;
import com.psp.instapay.model.service.AccountService;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import com.psp.instapay.model.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllAccounts() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Accounts retrieved successfully")
                        .data(accountService.getAllAccounts())
                        .build()
        );
    }

    @GetMapping("/{bankName}")
    public ResponseEntity<ApiResponse> getAccountsByBankName(@PathVariable String bankName) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Accounts retrieved successfully")
                        .data(accountService.getAllAccountsByBankName(bankName))
                        .build()
        );
    }

    @PostMapping("/accdetails")
    public ResponseEntity<ApiResponse> getAccountDetails(@Valid @RequestBody AccountDetailsRequest accountDetailsRequest) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Account details retrieved successfully")
                        .data(accountService.getAccountByAccountNumber(accountDetailsRequest))
                        .build()
        );
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> addAccountsByCard(@Valid @RequestBody GetAccountsRequest getAccountsRequest) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Account created successfully")
                        .data(accountService.addAccounts(getAccountsRequest))
                        .build()
        );
    }

    @PostMapping("/transactions/history")
    public ResponseEntity<ApiResponse> getTransactionHistory(@Valid @RequestBody AccountDetailsRequest accountDetailsRequest) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Transaction history retrieved successfully")
                        .data(accountService.getAccountTransactionHistory(accountDetailsRequest))
                        .build()
        );
    }
}

package com.psp.instapay.controller;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Account deleted successfully")
                        .build()
        );
    }
}

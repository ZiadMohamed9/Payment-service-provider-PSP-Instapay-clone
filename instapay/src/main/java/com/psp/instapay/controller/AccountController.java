package com.psp.instapay.controller;

import com.psp.instapay.model.service.AccountService;
import com.psp.instapay.model.dto.request.AddAccountRequest;
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
        try {
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .status(HttpStatus.OK)
                            .message("Accounts retrieved successfully")
                            .data(accountService.getAllAccounts())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ApiResponse.builder()
                                    .status(HttpStatus.NOT_FOUND)
                                    .message("Error retrieving accounts: " + e.getMessage())
                                    .build()
                    );
        }
    }

    @GetMapping("/{bankName}")
    public ResponseEntity<ApiResponse> getAccountsByBankName(@PathVariable String bankName) {
        try {
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .status(HttpStatus.OK)
                            .message("Accounts retrieved successfully")
                            .data(accountService.getAllAccountsByBankName(bankName))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ApiResponse.builder()
                                    .status(HttpStatus.NOT_FOUND)
                                    .message("Error retrieving accounts: " + e.getMessage())
                                    .build()
                    );
        }
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> addAccount(@Valid @RequestBody AddAccountRequest addAccountRequest) {
        try {
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .status(HttpStatus.OK)
                            .message("Account created successfully")
                            .data(accountService.addAccount(addAccountRequest))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ApiResponse.builder()
                                    .status(HttpStatus.NOT_FOUND)
                                    .message("Error adding account: " + e.getMessage())
                                    .build()
                    );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable Long id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .status(HttpStatus.OK)
                            .message("Account deleted successfully")
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ApiResponse.builder()
                                    .status(HttpStatus.NOT_FOUND)
                                    .message("Error deleting account: " + e.getMessage())
                                    .build()
                    );
        }
    }
}

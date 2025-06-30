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

/**
 * REST controller for managing account-related operations.
 * Provides endpoints for retrieving, adding, and managing account details.
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    /**
     * Retrieves all accounts.
     *
     * @return a ResponseEntity containing an ApiResponse with the list of all accounts
     */
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

    /**
     * Retrieves accounts by the specified bank name.
     *
     * @param bankName the name of the bank
     * @return a ResponseEntity containing an ApiResponse with the accounts for the specified bank
     */
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

    /**
     * Retrieves account details based on the provided request.
     *
     * @param accountDetailsRequest the request containing account details
     * @return a ResponseEntity containing an ApiResponse with the account details
     */
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

    /**
     * Adds accounts based on the provided card details.
     *
     * @param getAccountsRequest the request containing card details for account creation
     * @return a ResponseEntity containing an ApiResponse with the created account details
     */
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

    /**
     * Retrieves the transaction history for a specific account.
     *
     * @param accountDetailsRequest the request containing account details
     * @return a ResponseEntity containing an ApiResponse with the transaction history
     */
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
package com.psp.instapay.controller;

import com.psp.instapay.model.dto.request.AccountDetailsRequest;
import com.psp.instapay.model.dto.response.ResponseDto;
import com.psp.instapay.model.service.AccountService;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing account-related operations.
 * Provides endpoints for retrieving, adding, and managing account details.
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Validated
@Tag(
        name = "Accounts Management",
        description = "Endpoints for managing accounts, including retrieval and creation of accounts."
)
public class AccountController {
    private final AccountService accountService;

    /**
     * Retrieves all accounts.
     *
     * @return a ResponseEntity containing an ResponseDto with the list of all accounts
     */
    @Operation(
            summary = "Get All Accounts API",
            description = "Retrieves all of the authenticated user's accounts from the system."
    )
    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAllAccounts() {
        return ResponseEntity.ok(
                ResponseDto.builder()
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
     * @return a ResponseEntity containing an ResponseDto with the accounts for the specified bank
     */
    @Operation(
            summary = "Get Accounts by Bank Name API",
            description = "Retrieves all accounts associated with the specified bank name."
    )
    @GetMapping("/{bankName}")
    public ResponseEntity<ResponseDto> getAccountsByBankName(
            @PathVariable
            @NotBlank(message = "Bank name cannot be blank")
            @Size(max = 3, message = "Enter the first character of each word in the bank name")
            String bankName) {
        return ResponseEntity.ok(
                ResponseDto.builder()
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
     * @return a ResponseEntity containing an ResponseDto with the account details
     */
    @Operation(
            summary = "Get Account Details API",
            description = "Retrieves the details of an account based on the provided account number."
    )
    @PostMapping("/accdetails")
    public ResponseEntity<ResponseDto> getAccountDetails(@Valid @RequestBody AccountDetailsRequest accountDetailsRequest) {
        return ResponseEntity.ok(
                ResponseDto.builder()
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
     * @return a ResponseEntity containing an ResponseDto with the created account details
     */
    @Operation(
            summary = "Fetch Accounts from the associated Bank by Card details",
            description = "Fetches accounts from the associated bank using the provided card details."
    )
    @PostMapping("/")
    public ResponseEntity<ResponseDto> addAccountsByCard(@Valid @RequestBody GetAccountsRequest getAccountsRequest) {
        return ResponseEntity.ok(
                ResponseDto.builder()
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
     * @return a ResponseEntity containing an ResponseDto with the transaction history
     */
    @PostMapping("/transactions/history")
    public ResponseEntity<ResponseDto> getTransactionHistory(@Valid @RequestBody AccountDetailsRequest accountDetailsRequest) {
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("Transaction history retrieved successfully")
                        .data(accountService.getAccountTransactionHistory(accountDetailsRequest))
                        .build()
        );
    }
}
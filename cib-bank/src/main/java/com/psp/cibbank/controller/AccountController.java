package com.psp.cibbank.controller;

import com.psp.cibbank.model.dto.request.GetAccountsRequest;
import com.psp.cibbank.model.dto.response.GetAccountsResponse;
import com.psp.cibbank.model.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing account-related operations.
 * Provides endpoints to retrieve account balances and account details.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    /**
     * Retrieves the balance of a specific account.
     *
     * @param accountNumber the account number for which the balance is requested
     * @return the balance of the specified account
     */
    @PostMapping("/balance")
    public Double getBalance(@RequestBody String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    /**
     * Retrieves account details associated with a card.
     *
     * @param getAccountsRequest the request object containing card details
     * @return a response object containing account details
     */
    @PostMapping("/")
    public GetAccountsResponse getAccounts(@Valid @RequestBody GetAccountsRequest getAccountsRequest) {
        return accountService.getAccountsByCard(getAccountsRequest);
    }
}
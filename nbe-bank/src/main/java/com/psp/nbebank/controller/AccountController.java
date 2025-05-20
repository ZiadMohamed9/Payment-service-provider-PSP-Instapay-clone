package com.psp.nbebank.controller;

import com.psp.nbebank.model.dto.request.GetAccountsRequest;
import com.psp.nbebank.model.dto.response.GetAccountsResponse;
import com.psp.nbebank.model.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{accountNumber}/balance")
    public Double getBalance(@PathVariable String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    @PostMapping("/")
    public GetAccountsResponse getAccounts(@Valid @RequestBody GetAccountsRequest getAccountsRequest) {
        return accountService.getAccountsByCard(getAccountsRequest);
    }
}

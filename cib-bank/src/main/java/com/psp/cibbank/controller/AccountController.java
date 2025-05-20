package com.psp.cibbank.controller;

import com.psp.cibbank.model.dto.request.GetAccountsRequest;
import com.psp.cibbank.model.dto.response.GetAccountsResponse;
import com.psp.cibbank.model.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/balance")
    public Double getBalance(@RequestBody String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    @PostMapping("/")
    public GetAccountsResponse getAccounts(@Valid @RequestBody GetAccountsRequest getAccountsRequest) {
        return accountService.getAccountsByCard(getAccountsRequest);
    }
}

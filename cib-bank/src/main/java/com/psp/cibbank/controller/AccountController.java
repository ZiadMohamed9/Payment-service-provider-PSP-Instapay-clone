package com.psp.cibbank.controller;

import com.psp.cibbank.model.dto.request.GetAccountsRequest;
import com.psp.cibbank.model.dto.response.GetAccountsResponse;
import com.psp.cibbank.model.service.AccountService;
import com.psp.cibbank.model.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<GetAccountsResponse> getAccounts(@RequestBody GetAccountsRequest getAccountsRequest) {
        return accountService.getAccountsByCard(getAccountsRequest);
    }
}

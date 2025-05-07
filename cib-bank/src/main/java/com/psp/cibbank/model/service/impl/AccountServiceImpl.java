package com.psp.cibbank.model.service.impl;

import com.psp.cibbank.model.repository.AccountRepository;
import com.psp.cibbank.model.service.AccountService;
import com.psp.cibbank.common.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Double getBalance(String accountNumber) {
        if (!accountRepository.existsByAccountNumber(accountNumber))
            throw new AccountNotFoundException("Account not found");

        return accountRepository.findBalanceByAccountNumber(accountNumber);
    }
}

package com.psp.nbebank.model.service.impl;

import com.psp.nbebank.common.exception.AccountNotFoundException;
import com.psp.nbebank.model.repository.AccountRepository;
import com.psp.nbebank.model.service.AccountService;
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

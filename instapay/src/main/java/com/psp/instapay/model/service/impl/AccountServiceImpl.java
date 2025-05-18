package com.psp.instapay.model.service.impl;

import com.psp.instapay.common.exception.*;
import com.psp.instapay.model.dto.AccountDTO;
import com.psp.instapay.model.dto.request.AccountDetailsRequest;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import com.psp.instapay.model.dto.response.GetAccountsResponse;
import com.psp.instapay.model.entity.Account;
import com.psp.instapay.model.entity.Bank;
import com.psp.instapay.model.entity.User;
import com.psp.instapay.model.repository.AccountRepository;
import com.psp.instapay.model.repository.BankRepository;
import com.psp.instapay.model.repository.UserRepository;
import com.psp.instapay.model.service.AccountService;
import com.psp.instapay.common.client.BankClientFactory;
import com.psp.instapay.common.mapper.AccountMapper;
import com.psp.instapay.common.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final AccountMapper accountMapper;
    private final BankClientFactory bankClientFactory;
    private final EncryptionUtil encryptionUtil;

    @Override
    public AccountDTO getAccountByAccountNumber(AccountDetailsRequest accountDetailsRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String accountNumber = accountDetailsRequest.getAccountNumber();

        return accountRepository.findByUserAndAccountNumber(user, accountNumber)
                .map(accountMapper::toAccountDTO)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return accountRepository.findAllByUser(user)
                .stream()
                .map(accountMapper::toAccountDTO)
                .toList();
    }

    @Override
    public List<AccountDTO> getAllAccountsByBankName(String bankName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Bank bank = bankRepository.findByName(bankName.toUpperCase())
                .orElseThrow(() -> new BankNotFoundException("Bank not found"));

        return accountRepository.findAllByUserAndBank(user, bank)
                .stream()
                .map(accountMapper::toAccountDTO)
                .toList();
    }

    @Override
    public List<AccountDTO> addAccounts(GetAccountsRequest getAccountsRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String bankName = getAccountsRequest.getBankName().toUpperCase();
        Bank bank = bankRepository.findByName(bankName)
                .orElseThrow(() -> new BankNotFoundException("Bank not found"));

        // Create a new request with an encrypted card number, PIN, and phone number
        GetAccountsRequest encryptedRequest = GetAccountsRequest.builder()
                .bankName(getAccountsRequest.getBankName())
                .cardNumber(encryptionUtil.encrypt(getAccountsRequest.getCardNumber()))
                .pin(encryptionUtil.encrypt(getAccountsRequest.getPin()))
                .phoneNumber(encryptionUtil.encryptPhoneNumber(user.getPhoneNumber()))
                .build();

        GetAccountsResponse accountsResponse = bankClientFactory.getBankClient(bankName)
                .getAccounts(encryptedRequest);


        Map<String, Double> accounts = accountsResponse.getAccounts();

        List<AccountDTO> accountDTOs = new ArrayList<>();
        for (String accountNumber : accounts.keySet()) {
            if (accountRepository.existsByUserAndAccountNumber(user, accountNumber)) {
                continue;
            }

            if (accountRepository.existsByAccountNumber(accountNumber)) {
                throw new CardNotFoundException("Invalid card number or PIN");
            }

            Account newAccount = new Account();
            newAccount.setAccountNumber(accountNumber);
            newAccount.setBalance(accounts.get(accountNumber));
            newAccount.setUser(user);
            newAccount.setBank(bank);
            accountRepository.save(newAccount);

            AccountDTO accountDTO = accountMapper.toAccountDTO(newAccount);
            accountDTOs.add(accountDTO);
        }

        return accountDTOs;
    }

    @Override
    @Transactional
    public void deleteAccount(String accountNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (accountRepository.existsByUserAndAccountNumber(user, accountNumber)) {
            accountRepository.deleteByAccountNumber(accountNumber);
        } else {
            throw new AccountNotFoundException("Account not found");
        }
    }
}

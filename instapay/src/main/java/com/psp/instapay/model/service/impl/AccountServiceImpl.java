package com.psp.instapay.model.service.impl;

import com.psp.instapay.common.exception.AccountAlreadyExistsException;
import com.psp.instapay.model.dto.AccountDTO;
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
import com.psp.instapay.common.exception.AccountNotFoundException;
import com.psp.instapay.common.exception.BankNotFoundException;
import com.psp.instapay.common.exception.UserNotFoundException;
import com.psp.instapay.common.mapper.AccountMapper;
import com.psp.instapay.common.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final AccountMapper accountMapper;
    private final BankClientFactory bankClientFactory;
    private final EncryptionUtil encryptionUtil;

    @Override
    public AccountDTO getAccountById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return accountRepository.findByUserAndId(user, id)
                .map(accountMapper::toAccountDTO)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Override
    public AccountDTO getAccountByAccountNumber(String accountNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

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

        // Create a new request with an encrypted card number and PIN
        GetAccountsRequest encryptedRequest = GetAccountsRequest.builder()
                .bankName(getAccountsRequest.getBankName())
                .cardNumber(encryptionUtil.encrypt(getAccountsRequest.getCardNumber()))
                .pin(encryptionUtil.encrypt(getAccountsRequest.getPin()))
                .build();

        List<GetAccountsResponse> accounts = bankClientFactory.getBankClient(bankName).getAccounts(encryptedRequest);

        if (accounts == null || accounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts found for the given card");
        }

        List<AccountDTO> accountDTOs = new ArrayList<>();
        for (GetAccountsResponse account : accounts) {
            if (accountRepository.existsByUserAndAccountNumber(user, account.getAccountNumber())) {
                continue;
            }

            if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
                throw new BadCredentialsException("Invalid card number or PIN");
            }

            Account newAccount = new Account();
            newAccount.setAccountNumber(account.getAccountNumber());
            newAccount.setBalance(account.getBalance());
            newAccount.setUser(user);
            newAccount.setBank(bank);
            accountRepository.save(newAccount);

            AccountDTO accountDTO = accountMapper.toAccountDTO(newAccount);
            accountDTOs.add(accountDTO);
        }

        return accountDTOs;
    }

    @Override
    public void deleteAccount(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (accountRepository.existsByUserAndId(user, id)) {
            accountRepository.deleteById(id);
        } else {
            throw new AccountNotFoundException("Account not found");
        }
    }
}

package com.psp.instapay.model.service.impl;

import com.psp.instapay.exception.AccountNotFoundException;
import com.psp.instapay.exception.BankNotFoundException;
import com.psp.instapay.exception.CardNotFoundException;
import com.psp.instapay.exception.UserNotFoundException;
import com.psp.instapay.mapper.TransactionMapper;
import com.psp.instapay.model.dto.AccountDTO;
import com.psp.instapay.model.dto.TransactionDTO;
import com.psp.instapay.model.dto.request.AccountDetailsRequest;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import com.psp.instapay.model.dto.response.GetAccountsResponse;
import com.psp.instapay.model.entity.Account;
import com.psp.instapay.model.entity.Bank;
import com.psp.instapay.model.entity.User;
import com.psp.instapay.model.repository.AccountRepository;
import com.psp.instapay.model.repository.BankRepository;
import com.psp.instapay.model.repository.TransactionRepository;
import com.psp.instapay.model.repository.UserRepository;
import com.psp.instapay.model.service.AccountService;
import com.psp.instapay.client.BankClientFactory;
import com.psp.instapay.mapper.AccountMapper;
import com.psp.instapay.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the AccountService interface.
 * Provides methods for managing accounts, including retrieval, addition, deletion, and transaction history.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final TransactionRepository transactionRepository;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;
    private final BankClientFactory bankClientFactory;
    private final EncryptionUtil encryptionUtil;

    /**
     * Retrieves account details by account number for the authenticated user.
     *
     * @param accountDetailsRequest The request containing the account number.
     * @return The account details as an AccountDTO.
     * @throws AccountNotFoundException If the account is not found.
     */
    @Override
    public AccountDTO getAccountByAccountNumber(AccountDetailsRequest accountDetailsRequest) {
        User user = getUser();

        String accountNumber = accountDetailsRequest.getAccountNumber();

        return accountRepository.findByUserAndAccountNumber(user, accountNumber)
                .map(accountMapper::toAccountDTO)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    /**
     * Retrieves all accounts associated with the authenticated user.
     *
     * @return A list of all accounts as AccountDTOs.
     */
    @Override
    public List<AccountDTO> getAllAccounts() {
        User user = getUser();

        return accountRepository.findAllByUser(user)
                .stream()
                .map(accountMapper::toAccountDTO)
                .toList();
    }

    /**
     * Retrieves all accounts associated with a specific bank name for the authenticated user.
     *
     * @param bankName The name of the bank.
     * @return A list of accounts associated with the specified bank as AccountDTOs.
     * @throws BankNotFoundException If the bank is not found.
     */
    @Override
    public List<AccountDTO> getAllAccountsByBankName(String bankName) {
        User user = getUser();

        Bank bank = bankRepository.findByName(bankName.toUpperCase())
                .orElseThrow(() -> new BankNotFoundException("Bank not found"));

        return accountRepository.findAllByUserAndBank(user, bank)
                .stream()
                .map(accountMapper::toAccountDTO)
                .toList();
    }

    /**
     * Adds multiple accounts to the system for the authenticated user.
     *
     * @param getAccountsRequest The request containing the account details to be added.
     * @return A list of the added accounts as AccountDTOs.
     * @throws BankNotFoundException If the bank is not found.
     * @throws CardNotFoundException If the card number or PIN is invalid.
     */
    @Override
    public List<AccountDTO> addAccounts(GetAccountsRequest getAccountsRequest) {
        User user = getUser();

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

    /**
     * Retrieves the transaction history for a specific account of the authenticated user.
     *
     * @param accountDetailsRequest The request containing the account number.
     * @return A list of transactions associated with the account as TransactionDTOs.
     * @throws AccountNotFoundException If the account is not found.
     */
    @Override
    @Transactional
    public List<TransactionDTO> getAccountTransactionHistory(AccountDetailsRequest accountDetailsRequest) {
        User user = getUser();
        String accountNumber = accountDetailsRequest.getAccountNumber();

        Account account = accountRepository.findByUserAndAccountNumber(user, accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        return transactionRepository.findAllByFromAccountOrToAccount(account, account)
                .stream()
                .map(transactionMapper::toTransactionDTO)
                .toList();
    }

    /**
     * Deletes an account by its account number for the authenticated user.
     *
     * @param accountNumber The account number of the account to be deleted.
     * @throws AccountNotFoundException If the account is not found.
     */
    @Override
    @Transactional
    public void deleteAccount(String accountNumber) {
        User user = getUser();

        if (accountRepository.existsByUserAndAccountNumber(user, accountNumber)) {
            accountRepository.deleteByAccountNumber(accountNumber);
        } else {
            throw new AccountNotFoundException("Account not found");
        }
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return The authenticated user as a User entity.
     * @throws UserNotFoundException If the user is not found.
     */
    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
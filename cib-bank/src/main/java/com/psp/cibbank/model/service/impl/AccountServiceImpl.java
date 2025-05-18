package com.psp.cibbank.model.service.impl;

import com.psp.cibbank.common.exception.CardNotFoundException;
import com.psp.cibbank.common.exception.CustomerNotFoundException;
import com.psp.cibbank.common.util.EncryptionUtil;
import com.psp.cibbank.model.dto.request.GetAccountsRequest;
import com.psp.cibbank.model.dto.response.GetAccountsResponse;
import com.psp.cibbank.model.entity.Account;
import com.psp.cibbank.model.entity.Card;
import com.psp.cibbank.model.entity.Customer;
import com.psp.cibbank.model.repository.AccountRepository;
import com.psp.cibbank.model.repository.CardRepository;
import com.psp.cibbank.model.repository.CustomerRepository;
import com.psp.cibbank.model.service.AccountService;
import com.psp.cibbank.common.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;
    private final EncryptionUtil encryptionUtil;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public Double getBalance(String accountNumber) {
        // Decrypt the account number
        String decryptedAccountNumber = encryptionUtil.decrypt(accountNumber);

        // Fetch the account using the decrypted account number
        Account account = accountRepository.findByAccountNumber(decryptedAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        // Return the balance of the account
        return account.getBalance();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public GetAccountsResponse getAccountsByCard(GetAccountsRequest getAccountsRequest) {
        // Decrypt the card number, PIN, and phone number
        String cardNumber = encryptionUtil.decrypt(getAccountsRequest.getCardNumber());
        String pin = encryptionUtil.decrypt(getAccountsRequest.getPin());
        String phoneNumber = encryptionUtil.decryptPhoneNumber(getAccountsRequest.getPhoneNumber());

        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Card card = cardRepository.findByCustomerAndCardNumberAndPin(customer, cardNumber, pin)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        // Fetch accounts associated with the card
        List<Account> accounts = accountRepository.findByCard(card);
        if (accounts == null || accounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts found for the given card");
        }

        // Convert the list to a hashmap
        Map<String, Double> accountMap = accounts.stream()
                .collect(java.util.stream.Collectors.toMap(Account::getAccountNumber, Account::getBalance));

        return new GetAccountsResponse(accountMap);
    }
}

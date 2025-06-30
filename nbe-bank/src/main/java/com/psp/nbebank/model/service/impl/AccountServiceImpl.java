package com.psp.nbebank.model.service.impl;

import com.psp.nbebank.common.exception.AccountNotFoundException;
import com.psp.nbebank.common.exception.CardNotFoundException;
import com.psp.nbebank.common.exception.CustomerNotFoundException;
import com.psp.nbebank.common.util.EncryptionUtil;
import com.psp.nbebank.model.dto.request.GetAccountsRequest;
import com.psp.nbebank.model.dto.response.GetAccountsResponse;
import com.psp.nbebank.model.entity.Account;
import com.psp.nbebank.model.entity.Card;
import com.psp.nbebank.model.entity.Customer;
import com.psp.nbebank.model.repository.AccountRepository;
import com.psp.nbebank.model.repository.CardRepository;
import com.psp.nbebank.model.repository.CustomerRepository;
import com.psp.nbebank.model.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Implementation of the AccountService interface.
 * Provides methods to retrieve account balances and account details.
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;
    private final EncryptionUtil encryptionUtil;

    /**
     * Retrieves the balance of a specific account.
     * The account number is decrypted before fetching the account details.
     *
     * @param accountNumber the encrypted account number
     * @return the balance of the account
     * @throws AccountNotFoundException if the account is not found
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Double getBalance(String accountNumber) {
        // Decrypt the account number
        String decryptedAccountNumber = encryptionUtil.decrypt(accountNumber);

        // Fetch the account using the decrypted account number
        Account account = accountRepository.findForShareByAccountNumber(decryptedAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        // Return the balance of the account
        return account.getBalance();
    }

    /**
     * Retrieves account details associated with a specific card.
     * The card number, PIN, and phone number are decrypted before fetching the details.
     *
     * @param getAccountsRequest the request object containing encrypted card details
     * @return a response object containing account details
     * @throws CustomerNotFoundException if the customer is not found
     * @throws CardNotFoundException if the card is not found
     * @throws AccountNotFoundException if no accounts are found for the given card
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public GetAccountsResponse getAccountsByCard(GetAccountsRequest getAccountsRequest) {
        // Decrypt the card number, PIN, and phone number
        String cardNumber = encryptionUtil.decrypt(getAccountsRequest.getCardNumber());
        String pin = encryptionUtil.decrypt(getAccountsRequest.getPin());
        String phoneNumber = encryptionUtil.decryptPhoneNumber(getAccountsRequest.getPhoneNumber());

        // Fetch the customer using the decrypted phone number
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        // Fetch the card using the customer, card number, and PIN
        Card card = cardRepository.findByCustomerAndCardNumberAndPin(customer, cardNumber, pin)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        // Fetch accounts associated with the card
        List<Account> accounts = accountRepository.findForShareByCard(card);
        if (accounts == null || accounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts found for the given card");
        }

        // Convert the list of accounts to a hashmap
        Map<String, Double> accountMap = accounts.stream()
                .collect(java.util.stream.Collectors.toMap(Account::getAccountNumber, Account::getBalance));

        return new GetAccountsResponse(accountMap);
    }
}
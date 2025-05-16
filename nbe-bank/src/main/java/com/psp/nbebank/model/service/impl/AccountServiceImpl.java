package com.psp.nbebank.model.service.impl;

import com.psp.nbebank.common.exception.AccountNotFoundException;
import com.psp.nbebank.common.exception.CardNotFoundException;
import com.psp.nbebank.common.util.EncryptionUtil;
import com.psp.nbebank.model.dto.request.GetAccountsRequest;
import com.psp.nbebank.model.dto.response.GetAccountsResponse;
import com.psp.nbebank.model.repository.AccountRepository;
import com.psp.nbebank.model.repository.CardRepository;
import com.psp.nbebank.model.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final EncryptionUtil encryptionUtil;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public Double getBalance(String accountNumber) {
        return accountRepository.findBalanceByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Override
    public List<GetAccountsResponse> getAccountsByCard(GetAccountsRequest getAccountsRequest) {
        // Decrypt the card number and PIN
        String cardNumber = encryptionUtil.decrypt(getAccountsRequest.getCardNumber());
        String pin = encryptionUtil.decrypt(getAccountsRequest.getPin());

        Long cardId = cardRepository.findByCardNumberAndPin(cardNumber, pin)
                .orElseThrow(() -> new CardNotFoundException("Card not found"))
                .getId();

        return accountRepository.findByCardId(cardId)
                .stream()
                .map(account -> new GetAccountsResponse(account.getAccountNumber(), account.getBalance()))
                .toList();
    }
}

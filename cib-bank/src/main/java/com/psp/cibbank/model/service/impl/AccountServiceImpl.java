package com.psp.cibbank.model.service.impl;

import com.psp.cibbank.common.exception.CardNotFoundException;
import com.psp.cibbank.common.util.EncryptionUtil;
import com.psp.cibbank.model.dto.request.GetAccountsRequest;
import com.psp.cibbank.model.dto.response.GetAccountsResponse;
import com.psp.cibbank.model.repository.AccountRepository;
import com.psp.cibbank.model.repository.CardRepository;
import com.psp.cibbank.model.service.AccountService;
import com.psp.cibbank.common.exception.AccountNotFoundException;
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

package com.psp.cibbank.model.service;

import com.psp.cibbank.model.dto.request.BankRequest;
import com.psp.cibbank.model.dto.response.TransactionResponse;
import com.psp.cibbank.model.entity.Transaction;

public interface TransactionService {
    TransactionResponse prepareTransaction(BankRequest request);

    TransactionResponse commitTransaction(Long transactionId);

    TransactionResponse rollbackTransaction(Long transactionId);

    Transaction getTransactionById(Long transactionId);
}

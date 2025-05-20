package com.psp.cibbank.model.service;

import com.psp.cibbank.model.dto.request.TransactionRequest;
import com.psp.cibbank.model.dto.response.TransactionResponse;
import com.psp.cibbank.model.entity.Transaction;

public interface TransactionService {
    TransactionResponse prepareTransaction(TransactionRequest request);

    TransactionResponse commitTransaction(Long transactionId);

    TransactionResponse rollbackTransaction(Long transactionId);
}

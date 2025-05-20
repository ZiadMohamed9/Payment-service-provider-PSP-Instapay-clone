package com.psp.nbebank.model.service;

import com.psp.nbebank.model.dto.request.TransactionRequest;
import com.psp.nbebank.model.dto.response.TransactionResponse;
import com.psp.nbebank.model.entity.Transaction;

public interface TransactionService {
    TransactionResponse prepareTransaction(TransactionRequest request);

    TransactionResponse commitTransaction(Long transactionId);

    TransactionResponse rollbackTransaction(Long transactionId);
}

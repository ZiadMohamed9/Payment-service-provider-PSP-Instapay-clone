package com.psp.instapay.model.service;

import com.psp.instapay.model.dto.TransactionDTO;
import com.psp.instapay.model.dto.request.SendMoneyRequest;
import com.psp.instapay.model.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse sendMoney(SendMoneyRequest request);

    List<TransactionDTO> getTransactionHistory();
}
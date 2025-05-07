package com.psp.instapay.model.service;

import com.psp.instapay.model.dto.request.SendMoneyRequest;
import com.psp.instapay.model.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse sendMoney(SendMoneyRequest request);
} 
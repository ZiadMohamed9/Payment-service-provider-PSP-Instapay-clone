package com.psp.instapay.common.client;

import com.psp.instapay.model.dto.request.BankRequest;
import com.psp.instapay.model.dto.request.SendMoneyRequest;
import com.psp.instapay.model.dto.response.ApiResponse;

public interface BankClient {
    ApiResponse getBalance(String accountNumber);

    ApiResponse findUserByPhoneNumber(String phoneNumber);

    ApiResponse prepareTransaction(BankRequest request);

    ApiResponse commitTransaction(Long transactionId);

    ApiResponse rollbackTransaction(Long transactionId);
}

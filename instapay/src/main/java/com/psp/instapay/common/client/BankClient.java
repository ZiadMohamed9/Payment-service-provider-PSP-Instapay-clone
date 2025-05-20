package com.psp.instapay.common.client;

import com.psp.instapay.model.dto.request.TransactionRequest;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import com.psp.instapay.model.dto.response.ApiResponse;
import com.psp.instapay.model.dto.response.GetAccountsResponse;
import com.psp.instapay.model.dto.response.TransactionResponse;

public interface BankClient {
    Double getBalance(String accountNumber);

    GetAccountsResponse getAccounts(GetAccountsRequest getAccountsRequest);

    ApiResponse findUserByPhoneNumber(String phoneNumber);

    TransactionResponse prepareTransaction(TransactionRequest request);

    TransactionResponse commitTransaction(Long transactionId);

    TransactionResponse rollbackTransaction(Long transactionId);
}

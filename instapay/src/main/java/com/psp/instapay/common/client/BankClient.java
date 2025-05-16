package com.psp.instapay.common.client;

import com.psp.instapay.model.dto.request.BankRequest;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import com.psp.instapay.model.dto.response.ApiResponse;
import com.psp.instapay.model.dto.response.GetAccountsResponse;
import com.psp.instapay.model.dto.response.TransactionResponse;

import java.util.List;

public interface BankClient {
    Double getBalance(String accountNumber);

    List<GetAccountsResponse> getAccounts(GetAccountsRequest getAccountsRequest);

    ApiResponse findUserByPhoneNumber(String phoneNumber);

    TransactionResponse prepareTransaction(BankRequest request);

    TransactionResponse commitTransaction(Long transactionId);

    TransactionResponse rollbackTransaction(Long transactionId);
}

package com.psp.cibbank.model.service;

import com.psp.cibbank.model.dto.request.GetAccountsRequest;
import com.psp.cibbank.model.dto.response.GetAccountsResponse;

public interface AccountService {
    Double getBalance(String accountNumber);

    GetAccountsResponse getAccountsByCard(GetAccountsRequest getAccountsRequest);
}

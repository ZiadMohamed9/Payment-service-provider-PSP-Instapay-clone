package com.psp.cibbank.model.service;

import com.psp.cibbank.model.dto.request.GetAccountsRequest;
import com.psp.cibbank.model.dto.response.GetAccountsResponse;

import java.util.List;

public interface AccountService {
    Double getBalance(String accountNumber);

    List<GetAccountsResponse> getAccountsByCard(GetAccountsRequest getAccountsRequest);
}

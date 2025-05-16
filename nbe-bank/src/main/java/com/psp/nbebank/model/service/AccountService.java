package com.psp.nbebank.model.service;

import com.psp.nbebank.model.dto.request.GetAccountsRequest;
import com.psp.nbebank.model.dto.response.GetAccountsResponse;

import java.util.List;

public interface AccountService {
    Double getBalance(String accountNumber);

    List<GetAccountsResponse> getAccountsByCard(GetAccountsRequest getAccountsRequest);
}

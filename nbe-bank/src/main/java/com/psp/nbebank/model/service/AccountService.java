package com.psp.nbebank.model.service;

import com.psp.nbebank.model.dto.request.GetAccountsRequest;
import com.psp.nbebank.model.dto.response.GetAccountsResponse;

/**
 * Service interface for account-related operations.
 * Provides methods to retrieve account balances and account details.
 */
public interface AccountService {

    /**
     * Retrieves the balance of a specific account.
     *
     * @param accountNumber the unique identifier of the account
     * @return the balance of the account
     */
    Double getBalance(String accountNumber);

    /**
     * Retrieves account details associated with a specific card.
     *
     * @param getAccountsRequest the request object containing card details
     * @return a response object containing account details
     */
    GetAccountsResponse getAccountsByCard(GetAccountsRequest getAccountsRequest);
}
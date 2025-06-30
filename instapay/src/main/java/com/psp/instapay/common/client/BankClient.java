package com.psp.instapay.common.client;

import com.psp.instapay.model.dto.request.TransactionRequest;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import com.psp.instapay.model.dto.response.ApiResponse;
import com.psp.instapay.model.dto.response.GetAccountsResponse;
import com.psp.instapay.model.dto.response.TransactionResponse;
import org.springframework.http.ResponseEntity;

/**
 * Interface defining the contract for bank client operations.
 * Provides methods for interacting with bank APIs, such as retrieving account balances,
 * fetching account details, and managing transactions.
 */
public interface BankClient {

    /**
     * Retrieves the balance of a specific account.
     *
     * @param accountNumber the account number to retrieve the balance for
     * @return the balance of the account as a Double
     */
    Double getBalance(String accountNumber);

    /**
     * Retrieves the accounts associated with a specific request.
     *
     * @param getAccountsRequest the request containing account retrieval details
     * @return a GetAccountsResponse containing the account details
     */
    GetAccountsResponse getAccounts(GetAccountsRequest getAccountsRequest);

    /**
     * Retrieves customer details by their phone number.
     *
     * @param phoneNumber the phone number of the customer
     * @return a ResponseEntity containing an ApiResponse with the customer details
     */
    ResponseEntity<ApiResponse> getCustomerByPhoneNumber(String phoneNumber);

    /**
     * Prepares a transaction based on the provided request.
     *
     * @param request the transaction request containing transaction details
     * @return a TransactionResponse containing the prepared transaction details
     */
    TransactionResponse prepareTransaction(TransactionRequest request);

    /**
     * Commits a transaction using its ID.
     *
     * @param transactionId the ID of the transaction to commit
     * @return a TransactionResponse containing the committed transaction details
     */
    TransactionResponse commitTransaction(Long transactionId);

    /**
     * Rolls back a transaction using its ID.
     *
     * @param transactionId the ID of the transaction to roll back
     * @return a TransactionResponse containing the rolled-back transaction details
     */
    TransactionResponse rollbackTransaction(Long transactionId);
}
package com.psp.instapay.client.banks;

import com.psp.instapay.client.BankClient;
import com.psp.instapay.config.CibClientConfig;
import com.psp.instapay.model.dto.request.TransactionRequest;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import com.psp.instapay.model.dto.response.ApiResponse;
import com.psp.instapay.model.dto.response.GetAccountsResponse;
import com.psp.instapay.model.dto.response.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client interface for interacting with the CIB bank API.
 * Extends the BankClient interface to provide specific implementations for CIB bank.
 */
@FeignClient(name = "cib", url = "http://localhost:8090", configuration = CibClientConfig.class)
public interface CIBClient extends BankClient {

    /**
     * Retrieves the balance of a specific account.
     *
     * @param accountNumber the account number to retrieve the balance for
     * @return the balance of the account as a Double
     */
    @Override
    @PostMapping("/api/v1/accounts/balance")
    Double getBalance(@RequestBody String accountNumber);

    /**
     * Retrieves the accounts associated with a specific request.
     *
     * @param getAccountsRequest the request containing account retrieval details
     * @return a GetAccountsResponse containing the account details
     */
    @Override
    @PostMapping("/api/v1/accounts/")
    GetAccountsResponse getAccounts(@RequestBody GetAccountsRequest getAccountsRequest);

    /**
     * Retrieves customer details by their phone number.
     *
     * @param phoneNumber the phone number of the customer
     * @return a ResponseEntity containing an ApiResponse with the customer details
     */
    @Override
    @PostMapping("/api/v1/customers/phone")
    ResponseEntity<ApiResponse> getCustomerByPhoneNumber(@RequestBody String phoneNumber);

    /**
     * Prepares a transaction based on the provided request.
     *
     * @param request the transaction request containing transaction details
     * @return a TransactionResponse containing the prepared transaction details
     */
    @Override
    @PostMapping("/api/v1/transactions/prepare")
    TransactionResponse prepareTransaction(@RequestBody TransactionRequest request);

    /**
     * Commits a transaction using its ID.
     *
     * @param transactionId the ID of the transaction to commit
     * @return a TransactionResponse containing the committed transaction details
     */
    @Override
    @PostMapping("/api/v1/transactions/commit")
    TransactionResponse commitTransaction(@RequestBody Long transactionId);

    /**
     * Rolls back a transaction using its ID.
     *
     * @param transactionId the ID of the transaction to roll back
     * @return a TransactionResponse containing the rolled-back transaction details
     */
    @Override
    @PostMapping("/api/v1/transactions/rollback")
    TransactionResponse rollbackTransaction(@RequestBody Long transactionId);
}
package com.psp.instapay.model.service;

import com.psp.instapay.model.dto.TransactionDTO;
import com.psp.instapay.model.dto.request.SendMoneyRequest;
import com.psp.instapay.model.dto.response.TransactionResponse;

import java.util.List;

/**
 * Service interface for managing transactions.
 * Provides methods for sending money and retrieving transaction history.
 */
public interface TransactionService {

    /**
     * Sends money from one account to another.
     *
     * @param request The request containing details of the transaction, such as sender, receiver, and amount.
     * @return A TransactionResponse containing details of the completed transaction.
     */
    TransactionResponse sendMoney(SendMoneyRequest request);

    /**
     * Retrieves the transaction history for all accounts.
     *
     * @return A list of transactions as TransactionDTOs.
     */
    List<TransactionDTO> getTransactionHistory();
}
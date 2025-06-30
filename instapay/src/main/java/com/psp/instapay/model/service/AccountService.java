package com.psp.instapay.model.service;

import com.psp.instapay.model.dto.AccountDTO;
import com.psp.instapay.model.dto.TransactionDTO;
import com.psp.instapay.model.dto.request.AccountDetailsRequest;
import com.psp.instapay.model.dto.request.GetAccountsRequest;

import java.util.List;

/**
 * Service interface for managing accounts.
 * Provides methods for retrieving, adding, and deleting accounts, as well as fetching transaction history.
 */
public interface AccountService {

    /**
     * Retrieves account details by account number.
     *
     * @param accountDetailsRequest The request containing the account number.
     * @return The account details as an AccountDTO.
     */
    AccountDTO getAccountByAccountNumber(AccountDetailsRequest accountDetailsRequest);

    /**
     * Retrieves all accounts in the system.
     *
     * @return A list of all accounts as AccountDTOs.
     */
    List<AccountDTO> getAllAccounts();

    /**
     * Retrieves all accounts associated with a specific bank name.
     *
     * @param bankName The name of the bank.
     * @return A list of accounts associated with the specified bank as AccountDTOs.
     */
    List<AccountDTO> getAllAccountsByBankName(String bankName);

    /**
     * Adds multiple accounts to the system.
     *
     * @param addAccountRequest The request containing the account details to be added.
     * @return A list of the added accounts as AccountDTOs.
     */
    List<AccountDTO> addAccounts(GetAccountsRequest addAccountRequest);

    /**
     * Retrieves the transaction history for a specific account.
     *
     * @param accountNumber The request containing the account number.
     * @return A list of transactions associated with the account as TransactionDTOs.
     */
    List<TransactionDTO> getAccountTransactionHistory(AccountDetailsRequest accountNumber);

    /**
     * Deletes an account by its account number.
     *
     * @param accountNumber The account number of the account to be deleted.
     */
    void deleteAccount(String accountNumber);
}
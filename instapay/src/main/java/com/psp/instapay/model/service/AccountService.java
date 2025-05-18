package com.psp.instapay.model.service;

import com.psp.instapay.model.dto.AccountDTO;
import com.psp.instapay.model.dto.request.AccountDetailsRequest;
import com.psp.instapay.model.dto.request.GetAccountsRequest;

import java.util.List;

public interface AccountService {
    AccountDTO getAccountByAccountNumber(AccountDetailsRequest accountDetailsRequest);

    List<AccountDTO> getAllAccounts();

    List<AccountDTO> getAllAccountsByBankName(String bankName);

    List<AccountDTO> addAccounts(GetAccountsRequest addAccountRequest);

    void deleteAccount(String accountNumber);
}

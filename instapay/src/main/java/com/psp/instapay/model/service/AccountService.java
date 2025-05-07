package com.psp.instapay.model.service;

import com.psp.instapay.model.dto.AccountDTO;
import com.psp.instapay.model.dto.request.AddAccountRequest;

import java.util.List;

public interface AccountService {
    AccountDTO getAccountById(Long id);

    AccountDTO getAccountByAccountNumber(String accountNumber);

    List<AccountDTO> getAllAccounts();

    List<AccountDTO> getAllAccountsByBankName(String bankName);

    AccountDTO addAccount(AddAccountRequest addAccountRequest);

    void deleteAccount(Long id);
}

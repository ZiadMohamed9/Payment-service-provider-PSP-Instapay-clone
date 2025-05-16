package com.psp.instapay.common.mapper;

import com.psp.instapay.model.dto.AccountDTO;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import com.psp.instapay.model.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toAccountDTO(Account account);

    Account toAccount(GetAccountsRequest addAccountRequest);

    Account toAccount(AccountDTO accountDTO);
}

package com.psp.instapay.common.mapper;

import com.psp.instapay.model.dto.AccountDTO;
import com.psp.instapay.model.entity.Account;
import com.psp.instapay.model.dto.request.AddAccountRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toAccountDTO(Account account);

    Account toAccount(AddAccountRequest addAccountRequest);

    Account toAccount(AccountDTO accountDTO);
}

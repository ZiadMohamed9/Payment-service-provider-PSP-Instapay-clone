package com.psp.instapay.common.mapper;

import com.psp.instapay.model.dto.AccountDTO;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import com.psp.instapay.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "bank.name", target = "bankName")
    AccountDTO toAccountDTO(Account account);
}

package com.psp.instapay.common.mapper;

import com.psp.instapay.model.dto.TransactionDTO;
import com.psp.instapay.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "fromAccount.accountNumber", target = "fromAccountNumber")
    @Mapping(source = "toAccount.accountNumber", target = "toAccountNumber")
    @Mapping(source = "fromBank.name", target = "fromBankName")
    @Mapping(source = "toBank.name", target = "toBankName")
    TransactionDTO toTransactionDTO(Transaction transaction);
}

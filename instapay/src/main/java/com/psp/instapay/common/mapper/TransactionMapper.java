package com.psp.instapay.common.mapper;

import com.psp.instapay.model.dto.TransactionDTO;
import com.psp.instapay.model.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDTO toTransactionDTO(Transaction transaction);
}

package com.psp.instapay.mapper;

import com.psp.instapay.model.dto.TransactionDTO;
import com.psp.instapay.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between Transaction entity and TransactionDTO.
 * Utilizes MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {

    /**
     * Maps a Transaction entity to a TransactionDTO.
     *
     * @param transaction the Transaction entity to be mapped
     * @return the mapped TransactionDTO
     */
    @Mapping(source = "fromAccount.accountNumber", target = "fromAccountNumber")
    @Mapping(source = "toAccount.accountNumber", target = "toAccountNumber")
    @Mapping(source = "fromBank.name", target = "fromBankName")
    @Mapping(source = "toBank.name", target = "toBankName")
    TransactionDTO toTransactionDTO(Transaction transaction);
}
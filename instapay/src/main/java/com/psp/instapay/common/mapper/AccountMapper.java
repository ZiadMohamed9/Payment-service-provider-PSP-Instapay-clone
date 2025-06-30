package com.psp.instapay.common.mapper;

import com.psp.instapay.model.dto.AccountDTO;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import com.psp.instapay.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between Account entity and AccountDTO.
 * Utilizes MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    /**
     * Maps an Account entity to an AccountDTO.
     *
     * @param account the Account entity to be mapped
     * @return the mapped AccountDTO
     */
    @Mapping(source = "bank.name", target = "bankName")
    AccountDTO toAccountDTO(Account account);
}
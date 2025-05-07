package com.psp.cibbank.model.dto.request;

import com.psp.cibbank.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankRequest {
    private String accountNumber;
    private TransactionType type;
    private Double amount;
}

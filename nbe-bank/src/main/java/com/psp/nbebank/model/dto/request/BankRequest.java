package com.psp.nbebank.model.dto.request;

import com.psp.nbebank.model.enums.TransactionType;
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

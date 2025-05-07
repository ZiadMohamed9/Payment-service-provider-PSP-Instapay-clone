package com.psp.nbebank.model.dto.response;

import com.psp.nbebank.model.enums.BankTransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long transactionId;
    private BankTransactionStatus status;
    private String message;
}

package com.psp.instapay.model.dto;

import com.psp.instapay.model.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private String fromAccountNumber;
    private String toAccountNumber;
    private String fromBankName;
    private String toBankName;
    private Double amount;
    private TransactionStatus status;
    private LocalDateTime transactionDate;
}

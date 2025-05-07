package com.psp.instapay.model.dto;

import com.psp.instapay.model.entity.Account;
import com.psp.instapay.model.entity.Bank;
import com.psp.instapay.model.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private Account fromAccount;
    private Account toAccount;
    private Bank fromBank;
    private Bank toBank;
    private BigDecimal amount;
    private TransactionStatus status;
    private LocalDateTime transactionDate;
}

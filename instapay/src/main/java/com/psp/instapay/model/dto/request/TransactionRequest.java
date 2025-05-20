package com.psp.instapay.model.dto.request;

import com.psp.instapay.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private String accountNumber;
    private TransactionType type;
    private Double amount;
}

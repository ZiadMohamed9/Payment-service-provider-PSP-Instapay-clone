package com.psp.instapay.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMoneyRequest {
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private Double amount;
}

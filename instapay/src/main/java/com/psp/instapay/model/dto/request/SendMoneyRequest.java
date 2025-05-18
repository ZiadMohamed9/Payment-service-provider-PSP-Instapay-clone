package com.psp.instapay.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMoneyRequest {
    @NotNull(message = "Source account number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    private String sourceAccountNumber;

    @NotNull(message = "Destination account number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    private String destinationAccountNumber;

    @NotNull(message = "Transaction type is required")
    private Double amount;
}

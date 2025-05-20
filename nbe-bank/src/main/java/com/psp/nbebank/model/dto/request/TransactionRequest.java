package com.psp.nbebank.model.dto.request;

import com.psp.nbebank.model.enums.TransactionType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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
public class TransactionRequest {
    @NotNull(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1", message = "Amount must be greater than 0")
    @DecimalMax(value = "50000", message = "Amount must be less than or equal to 50000")
    private Double amount;
}

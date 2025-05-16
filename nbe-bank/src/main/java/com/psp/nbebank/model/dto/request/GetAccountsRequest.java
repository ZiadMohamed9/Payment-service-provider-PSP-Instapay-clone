package com.psp.nbebank.model.dto.request;

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
public class GetAccountsRequest {
    @NotNull(message = "Bank is required")
    String bankName;

    @NotNull(message = "Account number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    String cardNumber;

    @NotNull(message = "PIN is required")
    @Size(min = 4, max = 4, message = "PIN must be 4 digits")
    String pin;
}
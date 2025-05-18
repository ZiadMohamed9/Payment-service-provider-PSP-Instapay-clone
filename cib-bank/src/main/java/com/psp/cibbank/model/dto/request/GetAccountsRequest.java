package com.psp.cibbank.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountsRequest {
    String phoneNumber;

    @NotNull(message = "Bank is required")
    String bankName;

    @NotNull(message = "Account number is required")
    String cardNumber;

    @NotNull(message = "PIN is required")
    String pin;
}

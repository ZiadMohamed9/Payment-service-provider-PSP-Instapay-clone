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
public class AccountDetailsRequest {
    @NotNull(message = "Account number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    String accountNumber;
}

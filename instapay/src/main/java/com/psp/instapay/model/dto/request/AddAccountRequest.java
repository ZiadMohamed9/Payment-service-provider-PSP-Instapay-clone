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
public class AddAccountRequest {
    @NotNull(message = "Bank is required")
    String bankName;

    @NotNull(message = "Account number is required")
    @Size(min = 16, max = 16, message = "Account number must be 16 digits")
    String accountNumber;
}

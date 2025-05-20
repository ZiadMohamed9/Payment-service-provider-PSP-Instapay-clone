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
public class SignUpRequest {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Phone number is required")
    @Size(min = 11, max = 11, message = "Phone number should be 11 digits")
    private String phoneNumber;

    @NotNull(message = "Password is required")
    @Size(min = 8, max = 32, message = "Password size should be between 8 and 32 characters")
    private String password;
}

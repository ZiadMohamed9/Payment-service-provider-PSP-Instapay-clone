package com.psp.instapay.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleRequest {
    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Role is required")
    private String role;
}

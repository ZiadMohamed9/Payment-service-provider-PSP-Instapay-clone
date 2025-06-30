package com.psp.instapay.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object (DTO) for retrieving accounts response.
 * Represents the response payload containing account details and their balances.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAccountsResponse {

    /**
     * A map of account identifiers to their respective balances.
     * The key represents the account ID, and the value represents the balance.
     */
    private Map<String, Double> accounts;
}
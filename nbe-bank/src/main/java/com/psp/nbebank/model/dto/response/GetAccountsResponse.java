package com.psp.nbebank.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Data Transfer Object (DTO) for the response containing account details.
 * Encapsulates a map of account identifiers and their corresponding balances.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAccountsResponse {

    /**
     * A map where the key is the account identifier (e.g., account number)
     * and the value is the account balance.
     */
    private Map<String, Double> accounts;
}
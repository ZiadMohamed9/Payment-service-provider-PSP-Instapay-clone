package com.psp.instapay.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for bank details.
 * Represents the bank information including the bank's name.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankDTO {

    /**
     * The name of the bank.
     * Represents the bank's official name.
     */
    private String name;
}
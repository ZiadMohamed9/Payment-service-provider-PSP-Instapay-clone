package com.psp.cibbank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for customer details.
 * Encapsulates the basic information of a customer.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    /**
     * The name of the customer.
     */
    private String name;

    /**
     * The phone number of the customer.
     */
    private String phoneNumber;
}
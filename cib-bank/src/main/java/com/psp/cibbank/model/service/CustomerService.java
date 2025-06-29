package com.psp.cibbank.model.service;

import com.psp.cibbank.model.dto.CustomerDTO;

/**
 * Service interface for customer-related operations.
 * Provides methods to retrieve customer details and check customer existence.
 */
public interface CustomerService {

    /**
     * Retrieves customer details based on the provided phone number.
     *
     * @param phoneNumber the phone number of the customer
     * @return a CustomerDTO object containing the customer's details
     */
    CustomerDTO getCustomerByPhoneNumber(String phoneNumber);

    /**
     * Checks if a customer exists based on the provided phone number.
     *
     * @param phoneNumber the phone number of the customer
     * @return true if the customer exists, false otherwise
     */
    boolean isCustomerExists(String phoneNumber);
}
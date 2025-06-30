package com.psp.nbebank.model.service.impl;

import com.psp.nbebank.common.exception.CustomerNotFoundException;
import com.psp.nbebank.model.dto.CustomerDTO;
import com.psp.nbebank.model.repository.CustomerRepository;
import com.psp.nbebank.model.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CustomerService interface.
 * Provides methods to retrieve customer details and check customer existence.
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * Retrieves customer details based on the provided phone number.
     *
     * @param phoneNumber the phone number of the customer
     * @return a CustomerDTO object containing the customer's details
     * @throws CustomerNotFoundException if the customer is not found
     */
    @Override
    public CustomerDTO getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .map(customer -> new CustomerDTO(customer.getName(), customer.getPhoneNumber()))
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    /**
     * Checks if a customer exists based on the provided phone number.
     *
     * @param phoneNumber the phone number of the customer
     * @return true if the customer exists, false otherwise
     */
    @Override
    public boolean isCustomerExists(String phoneNumber) {
        return customerRepository.existsByPhoneNumber(phoneNumber);
    }
}
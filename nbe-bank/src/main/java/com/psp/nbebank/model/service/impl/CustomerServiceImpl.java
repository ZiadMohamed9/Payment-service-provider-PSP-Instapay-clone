package com.psp.nbebank.model.service.impl;

import com.psp.nbebank.common.exception.CustomerNotFoundException;
import com.psp.nbebank.model.dto.CustomerDTO;
import com.psp.nbebank.model.repository.CustomerRepository;
import com.psp.nbebank.model.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .map(customer -> new CustomerDTO(customer.getName(), customer.getPhoneNumber()))
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    @Override
    public boolean isCustomerExists(String phoneNumber) {
        return customerRepository.existsByPhoneNumber(phoneNumber);
    }
}

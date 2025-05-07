package com.psp.cibbank.model.service;

import com.psp.cibbank.model.dto.CustomerDTO;

public interface CustomerService {
    CustomerDTO getCustomerByPhoneNumber(String phoneNumber);

    boolean isCustomerExists(String phoneNumber);
}

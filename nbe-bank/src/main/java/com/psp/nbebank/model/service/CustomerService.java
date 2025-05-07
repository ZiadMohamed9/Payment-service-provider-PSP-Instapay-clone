package com.psp.nbebank.model.service;

import com.psp.nbebank.model.dto.CustomerDTO;

public interface CustomerService {
    CustomerDTO getCustomerByPhoneNumber(String phoneNumber);

    boolean isCustomerExists(String phoneNumber);
}

package com.psp.instapay.common.client.banks;

import com.psp.instapay.common.client.BankClient;
import com.psp.instapay.model.dto.request.BankRequest;
import com.psp.instapay.model.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "nbe", url = "http://localhost:8070")
public interface NBEClient extends BankClient {
    @Override
    @GetMapping("/api/v1/accounts/{accountNumber}/balance")
    ApiResponse getBalance(@PathVariable String accountNumber);

    @Override
    @GetMapping("/api/v1/users/phone/{phoneNumber}")
    ApiResponse findUserByPhoneNumber(@PathVariable String phoneNumber);

    @Override
    @PostMapping("/api/v1/transactions/prepare")
    ApiResponse prepareTransaction(@RequestBody BankRequest request);

    @Override
    @PostMapping("/api/v1/transactions/commit")
    ApiResponse commitTransaction(@RequestBody Long transactionId);

    @Override
    @PostMapping("/api/v1/transactions/rollback")
    ApiResponse rollbackTransaction(@RequestBody Long transactionId);
}

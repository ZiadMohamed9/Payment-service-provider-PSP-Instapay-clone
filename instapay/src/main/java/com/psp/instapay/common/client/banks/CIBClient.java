package com.psp.instapay.common.client.banks;

import com.psp.instapay.common.client.BankClient;
import com.psp.instapay.model.dto.request.BankRequest;
import com.psp.instapay.model.dto.request.GetAccountsRequest;
import com.psp.instapay.model.dto.response.ApiResponse;
import com.psp.instapay.model.dto.response.GetAccountsResponse;
import com.psp.instapay.model.dto.response.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "cib", url = "http://localhost:8090")
public interface CIBClient extends BankClient {
    @Override
    @GetMapping("/api/v1/accounts/{accountNumber}/balance")
    Double getBalance(@PathVariable String accountNumber);

    @Override
    @PostMapping("/api/v1/accounts/")
    List<GetAccountsResponse> getAccounts(@RequestBody GetAccountsRequest getAccountsRequest);

    @Override
    @GetMapping("/api/v1/users/phone/{phoneNumber}")
    ApiResponse findUserByPhoneNumber(@PathVariable String phoneNumber);

    @Override
    @PostMapping("/api/v1/transactions/prepare")
    TransactionResponse prepareTransaction(@RequestBody BankRequest request);

    @Override
    @PostMapping("/api/v1/transactions/commit")
    TransactionResponse commitTransaction(@RequestBody Long transactionId);

    @Override
    @PostMapping("/api/v1/transactions/rollback")
    TransactionResponse rollbackTransaction(@RequestBody Long transactionId);
}

package com.psp.nbebank.controller;

import com.psp.nbebank.model.dto.request.BankRequest;
import com.psp.nbebank.model.dto.response.ApiResponse;
import com.psp.nbebank.model.dto.response.TransactionResponse;
import com.psp.nbebank.model.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/prepare")
    public TransactionResponse prepareTransaction(@RequestBody BankRequest request) {
        return transactionService.prepareTransaction(request);
    }

    @PostMapping("/commit")
    public TransactionResponse commitTransaction(@RequestBody Long transactionId) {
        return transactionService.commitTransaction(transactionId);
    }

    @PostMapping("/rollback")
    public TransactionResponse rollbackTransaction(@RequestBody Long transactionId) {
        return transactionService.rollbackTransaction(transactionId);
    }

    private ApiResponse buildResponse(HttpStatus httpStatus, String message, TransactionResponse response) {
        return ApiResponse.builder()
                .status(httpStatus)
                .message(message)
                .data(response)
                .build();
    }
}

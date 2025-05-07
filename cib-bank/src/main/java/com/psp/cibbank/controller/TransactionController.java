package com.psp.cibbank.controller;

import com.psp.cibbank.model.dto.request.BankRequest;
import com.psp.cibbank.model.dto.response.ApiResponse;
import com.psp.cibbank.model.dto.response.TransactionResponse;
import com.psp.cibbank.model.enums.TransactionStatus;
import com.psp.cibbank.model.service.TransactionService;
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
    ApiResponse prepareTransaction(@RequestBody BankRequest request) {
        TransactionResponse response = transactionService.prepareTransaction(request);
        if (response.getStatus().equals(TransactionStatus.PREPARED)) {
            return buildResponse(HttpStatus.OK, "Transaction prepared successfully", response);
        }
        return buildResponse(HttpStatus.FORBIDDEN, "Transaction preparation failed", response);
    }

    @PostMapping("/commit")
    ApiResponse commitTransaction(@RequestBody Long transactionId) {
        TransactionResponse response = transactionService.commitTransaction(transactionId);
        if (response.getStatus().equals(TransactionStatus.COMMITTED)) {
            return buildResponse(HttpStatus.OK, "Transaction committed successfully", response);
        }
        return buildResponse(HttpStatus.FORBIDDEN, "Transaction commitment failed", response);
    }

    @PostMapping("/rollback")
    ApiResponse rollbackTransaction(@RequestBody Long transactionId) {
        TransactionResponse response = transactionService.rollbackTransaction(transactionId);
        if (response.getStatus().equals(TransactionStatus.ROLLED_BACK)) {
            return buildResponse(HttpStatus.OK, "Transaction rolled back successfully", response);
        }
        return buildResponse(HttpStatus.FORBIDDEN, "Transaction roll back failed", response);
    }

    private ApiResponse buildResponse(HttpStatus httpStatus, String message, TransactionResponse response) {
        return ApiResponse.builder()
                .status(httpStatus)
                .message(message)
                .data(response)
                .build();
    }
}

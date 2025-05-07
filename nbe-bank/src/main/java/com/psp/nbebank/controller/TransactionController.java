package com.psp.nbebank.controller;

import com.psp.nbebank.model.dto.request.BankRequest;
import com.psp.nbebank.model.dto.response.ApiResponse;
import com.psp.nbebank.model.enums.BankTransactionStatus;
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
    ApiResponse prepareTransaction(@RequestBody BankRequest request) {
        BankTransactionStatus status = transactionService.prepareTransaction(request).getStatus();
        if (status == BankTransactionStatus.PREPARED) {
            return buildResponse(HttpStatus.OK, "Transaction prepared successfully", status);
        }
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction preparation failed", status);
    }

    @PostMapping("/commit")
    ApiResponse commitTransaction(@RequestBody Long transactionId) {
        BankTransactionStatus status = transactionService.commitTransaction(transactionId).getStatus();
        if (status == BankTransactionStatus.COMMITTED) {
            return buildResponse(HttpStatus.OK, "Transaction committed successfully", status);
        }
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction commit failed", status);
    }

    @PostMapping("/rollback")
    ApiResponse rollbackTransaction(@RequestBody Long transactionId) {
        BankTransactionStatus status = transactionService.rollbackTransaction(transactionId).getStatus();
        if (status == BankTransactionStatus.ROLLED_BACK) {
            return buildResponse(HttpStatus.OK, "Transaction rolled back successfully", status);
        }
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction rollback failed", status);
    }

    private ApiResponse buildResponse(HttpStatus httpStatus, String message, BankTransactionStatus bankTransactionStatus) {
        return ApiResponse.builder()
                .status(httpStatus)
                .message(message)
                .data(bankTransactionStatus)
                .build();
    }
}

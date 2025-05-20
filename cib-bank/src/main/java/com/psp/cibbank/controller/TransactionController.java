package com.psp.cibbank.controller;

import com.psp.cibbank.model.dto.request.TransactionRequest;
import com.psp.cibbank.model.dto.response.TransactionResponse;
import com.psp.cibbank.model.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public TransactionResponse prepareTransaction(@Valid @RequestBody TransactionRequest request) {
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
}

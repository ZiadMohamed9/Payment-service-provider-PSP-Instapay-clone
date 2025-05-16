package com.psp.instapay.controller;

import com.psp.instapay.model.dto.request.SendMoneyRequest;
import com.psp.instapay.model.dto.response.ApiResponse;
import com.psp.instapay.model.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendMoney(@RequestBody SendMoneyRequest request) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .data(transactionService.sendMoney(request))
                        .message("Transaction successful")
                        .build()
        );
    }
}

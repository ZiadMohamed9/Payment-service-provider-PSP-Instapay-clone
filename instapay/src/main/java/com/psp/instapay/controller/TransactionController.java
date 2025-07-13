package com.psp.instapay.controller;

import com.psp.instapay.model.dto.request.SendMoneyRequest;
import com.psp.instapay.model.dto.response.ResponseDto;
import com.psp.instapay.model.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing transaction-related operations.
 * Provides endpoints for sending money and retrieving transaction history.
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * Sends money based on the provided request details.
     *
     * @param request the SendMoneyRequest containing transaction details
     * @return a ResponseEntity containing an ResponseDto with the transaction status and details
     */
    @PostMapping("/send")
    public ResponseEntity<ResponseDto> sendMoney(@Valid @RequestBody SendMoneyRequest request) {
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .data(transactionService.sendMoney(request))
                        .message("Transaction successful")
                        .build()
        );
    }

    /**
     * Retrieves the transaction history for the user.
     *
     * @return a ResponseEntity containing an ResponseDto with the transaction history
     */
    @PostMapping("/history")
    public ResponseEntity<ResponseDto> getTransactionHistory() {
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .data(transactionService.getTransactionHistory())
                        .message("Transaction history retrieved successfully")
                        .build()
        );
    }
}
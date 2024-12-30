package com.devsu.hackerearth.backend.account.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAll() {
        // api/transactions
        List<TransactionDto> transactions = transactionService.getAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> get(@PathVariable Long id) {
        // api/transactions/{id}
        TransactionDto transaction = transactionService.getById(id);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping
    public ResponseEntity<TransactionDto> create(@RequestBody TransactionDto transactionDto) {
        // api/transactions
        TransactionDto createdTransaction = transactionService.create(transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @GetMapping("/clients/{clientId}/report")
    public ResponseEntity<List<BankStatementDto>> report(
            @PathVariable Long clientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd) {
        // api/transactions/clients/{clientId}/report
        List<BankStatementDto> report = transactionService.getAllByAccountClientIdAndDateBetween(clientId, dateTransactionStart, dateTransactionEnd);
        return ResponseEntity.ok(report);
    }
}

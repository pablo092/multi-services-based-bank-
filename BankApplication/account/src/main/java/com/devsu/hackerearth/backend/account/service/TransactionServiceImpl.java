package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream()
                .map(transaction -> new TransactionDto(
                        transaction.getId(),
                        transaction.getDate(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getBalance(),
                        transaction.getAccountId()))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction with ID " + id + " not found"));
        return new TransactionDto(
                transaction.getId(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalance(),
                transaction.getAccountId());
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        Transaction transaction = new Transaction(
                transactionDto.getDate(),
                transactionDto.getType(),
                transactionDto.getAmount(),
                transactionDto.getBalance(),
                transactionDto.getAccountId());
        transaction = transactionRepository.save(transaction);
        return new TransactionDto(
                transaction.getId(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalance(),
                transaction.getAccountId());
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long accountId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        List<Transaction> transactions = transactionRepository.findAllByAccountIdAndDateBetween(accountId,
                dateTransactionStart, dateTransactionEnd);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account with ID " + accountId + " not found"));

        return transactions.stream()
                .map(transaction -> new BankStatementDto(
                        transaction.getDate(),
                        account.getClientId().toString(),
                        account.getNumber(),
                        account.getType(),
                        account.getInitialAmount(),
                        account.isActive(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getBalance()))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        Transaction transaction = transactionRepository.findTopByAccountIdOrderByDateDesc(accountId)
                .orElseThrow(() -> new RuntimeException("No transactions found for account ID " + accountId));
        return new TransactionDto(
                transaction.getId(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalance(),
                transaction.getAccountId());
    }
}

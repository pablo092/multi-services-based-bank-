package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream()
            .map(account -> new AccountDto(
                account.getId(),
                account.getNumber(),
                account.getType(),
                account.getInitialAmount(),
                account.isActive(),
                account.getClientId()
            ))
            .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Account with ID " + id + " not found"));
        return new AccountDto(
            account.getId(),
            account.getNumber(),
            account.getType(),
            account.getInitialAmount(),
            account.isActive(),
            account.getClientId()
        );
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        Account account = new Account(
            accountDto.getNumber(),
            accountDto.getType(),
            accountDto.getInitialAmount(),
            accountDto.isActive(),
            accountDto.getClientId()
        );
        account = accountRepository.save(account);
        return new AccountDto(
            account.getId(),
            account.getNumber(),
            account.getType(),
            account.getInitialAmount(),
            account.isActive(),
            account.getClientId()
        );
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        Account account = accountRepository.findById(accountDto.getId())
            .orElseThrow(() -> new RuntimeException("Account with ID " + accountDto.getId() + " not found"));

        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());

        account = accountRepository.save(account);
        return new AccountDto(
            account.getId(),
            account.getNumber(),
            account.getType(),
            account.getInitialAmount(),
            account.isActive(),
            account.getClientId()
        );
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Account with ID " + id + " not found"));

        if (partialAccountDto.isActive() != account.isActive()) {
            account.setActive(partialAccountDto.isActive());
        }

        account = accountRepository.save(account);
        return new AccountDto(
            account.getId(),
            account.getNumber(),
            account.getType(),
            account.getInitialAmount(),
            account.isActive(),
            account.getClientId()
        );
    }

    @Override
    public void deleteById(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account with ID " + id + " not found");
        }
        accountRepository.deleteById(id);
    }
}

package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.account.controller.AccountController;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@SpringBootTest
public class sampleTest {

    private AccountService accountService = mock(AccountService.class);
    private AccountController accountController = new AccountController(accountService);

    @Test
    void createAccountTest() {
        // Arrange
        AccountDto newAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
        AccountDto createdAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
        when(accountService.create(newAccount)).thenReturn(createdAccount);

        // Act
        ResponseEntity<AccountDto> response = accountController.create(newAccount);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdAccount, response.getBody());
    }

    @SuppressWarnings("null")
	@Test
    void getAllAccountsTest() {
        // Arrange
        List<AccountDto> mockAccounts = Arrays.asList(
                new AccountDto(1L, "number1", "savings", 100.0, true, 1L),
                new AccountDto(2L, "number2", "current", 200.0, false, 2L)
        );
        when(accountService.getAll()).thenReturn(mockAccounts);

        // Act
        ResponseEntity<List<AccountDto>> response = accountController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @SuppressWarnings("null")
	@Test
    void getAccountByIdTest() {
        // Arrange
        AccountDto mockAccount = new AccountDto(1L, "number", "savings", 100.0, true, 1L);
        when(accountService.getById(1L)).thenReturn(mockAccount);

        // Act
        ResponseEntity<AccountDto> response = accountController.get(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("number", response.getBody().getNumber());
    }

    @SuppressWarnings("null")
	@Test
    void updateAccountTest() {
        // Arrange
        AccountDto updatedAccount = new AccountDto(1L, "numberUpdated", "savings", 150.0, true, 1L);
        when(accountService.update(updatedAccount)).thenReturn(updatedAccount);

        // Act
        ResponseEntity<AccountDto> response = accountController.update(1L, updatedAccount);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("numberUpdated", response.getBody().getNumber());
    }

    @SuppressWarnings("null")
    @Test
    void partialUpdateAccountTest() {
        // Arrange
        PartialAccountDto partialUpdate = new PartialAccountDto(false);
        AccountDto updatedAccount = new AccountDto(1L, "number", "savings", 100.0, false, 1L);
        when(accountService.partialUpdate(1L, partialUpdate)).thenReturn(updatedAccount);

        // Act
        ResponseEntity<AccountDto> response = accountController.partialUpdate(1L, partialUpdate);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().isActive());
    }

    @Test
    void deleteAccountTest() {
        // Act
        ResponseEntity<Void> response = accountController.delete(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(accountService, times(1)).deleteById(1L);
    }
}

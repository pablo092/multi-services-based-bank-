package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @SuppressWarnings("null")
	@Test
    void integrationCreateAccountTest() {
        // Arrange
        AccountDto newAccount = new AccountDto(null, "12345", "savings", 1000.0, true, 1L);

        // Act
        ResponseEntity<AccountDto> response = restTemplate.postForEntity("/api/accounts", newAccount, AccountDto.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("12345", response.getBody().getNumber());
    }

    @SuppressWarnings("rawtypes")
	@Test
    void integrationGetAllAccountsTest() {
        // Act
        ResponseEntity<List> response = restTemplate.getForEntity("/api/accounts", List.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @SuppressWarnings("null")
	@Test
    void integrationGetAccountByIdTest() {
        // Arrange
        Long accountId = 1L;

        // Act
        ResponseEntity<AccountDto> response = restTemplate.getForEntity("/api/accounts/" + accountId, AccountDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(accountId, response.getBody().getId());
    }

    @Test
    void integrationDeleteAccountTest() {
        // Arrange
        Long accountId = 1L;

        // Act
        restTemplate.delete("/api/accounts/" + accountId);

        // Assert
        ResponseEntity<AccountDto> response = restTemplate.getForEntity("/api/accounts/" + accountId, AccountDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
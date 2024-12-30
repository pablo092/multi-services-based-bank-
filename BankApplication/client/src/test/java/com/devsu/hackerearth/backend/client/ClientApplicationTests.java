package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientApplicationTests {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("null")
    @Test
    void integrationCreateAndGetClientTest() {
        // Arrange
        String baseUrl = "http://localhost:" + port + "/api/clients";
        ClientDto newClient = new ClientDto(null, "Dni", "Name", "Password", "Gender", 25, "Address", "9999999999", true);

        // Act
        ResponseEntity<ClientDto> postResponse = restTemplate.postForEntity(baseUrl, newClient, ClientDto.class);

        // Assert
        assertEquals(201, postResponse.getStatusCodeValue());
        assertNotNull(postResponse.getBody());
        assertNotNull(postResponse.getBody().getId());

        // Act
        Long clientId = postResponse.getBody().getId();
        ResponseEntity<ClientDto> getResponse = restTemplate.getForEntity(baseUrl + "/" + clientId, ClientDto.class);

        // Assert
        assertEquals(200, getResponse.getStatusCodeValue());
        assertEquals("Name", getResponse.getBody().getName());
    }

    @Test
    @SuppressWarnings("null")
    void integrationDeleteClientTest() {
        // Arrange
        String baseUrl = "http://localhost:" + port + "/api/clients";
        ClientDto newClient = new ClientDto(null, "DniToDelete", "NameToDelete", "Password", "Gender", 30, "Address", "9999999999", true);

        // Act
        ResponseEntity<ClientDto> postResponse = restTemplate.postForEntity(baseUrl, newClient, ClientDto.class);
        Long clientId = postResponse.getBody().getId();

        // Act
        restTemplate.delete(baseUrl + "/" + clientId);

        // Act
        ResponseEntity<String> getResponse = restTemplate.getForEntity(baseUrl + "/" + clientId, String.class);

        // Assert
        assertEquals(404, getResponse.getStatusCodeValue());
    }

    @SuppressWarnings("null")
    @Test
    void integrationGetAllClientsTest() {
        // Arrange
        String baseUrl = "http://localhost:" + port + "/api/clients";

        // Act
        ResponseEntity<ClientDto[]> response = restTemplate.getForEntity(baseUrl, ClientDto[].class);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 0);
    }
}

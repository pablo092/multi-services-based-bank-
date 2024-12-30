package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@SpringBootTest
public class sampleTest {

    private ClientService clientService = mock(ClientService.class);
    private ClientController clientController = new ClientController(clientService);

    @Test
    void createClientTest() {
        // Arrange
        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        ClientDto createdClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999",
                true);
        when(clientService.create(newClient)).thenReturn(createdClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.create(newClient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdClient, response.getBody());
    }

    @SuppressWarnings("null")
    @Test
    void getAllClientsTest() {
        // Arrange
        List<ClientDto> mockClients = Arrays.asList(
                new ClientDto(1L, "Dni1", "Name1", "Password1", "Gender1", 25, "Address1", "Phone1", true),
                new ClientDto(2L, "Dni2", "Name2", "Password2", "Gender2", 30, "Address2", "Phone2", false));
        when(clientService.getAll()).thenReturn(mockClients);

        // Act
        ResponseEntity<List<ClientDto>> response = clientController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @SuppressWarnings("null")
    @Test
    void getClientByIdTest() {
        // Arrange
        ClientDto mockClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        when(clientService.getById(1L)).thenReturn(mockClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.get(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Name", response.getBody().getName());
    }

    @SuppressWarnings("null")
    @Test
    void updateClientTest() {
        // Arrange
        ClientDto updatedClient = new ClientDto(1L, "Dni", "UpdatedName", "Password", "Gender", 1, "Address",
                "9999999999", true);
        when(clientService.update(updatedClient)).thenReturn(updatedClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.update(1L, updatedClient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("UpdatedName", response.getBody().getName());
    }

    @SuppressWarnings("null")
    @Test
    void partialUpdateClientTest() {
        // Arrange
        PartialClientDto partialUpdate = new PartialClientDto(false);
        ClientDto updatedClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999",
                false);
        when(clientService.partialUpdate(1L, partialUpdate)).thenReturn(updatedClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.partialUpdate(1L, partialUpdate);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().isActive());
    }

    @Test
    void deleteClientTest() {
        // Act
        ResponseEntity<Void> response = clientController.delete(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clientService, times(1)).deleteById(1L);
    }
}

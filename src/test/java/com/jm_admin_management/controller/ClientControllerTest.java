package com.jm_admin_management.controller;

import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.service.ClientService;
import com.jm_admin_management.test_utils.factory.ClientTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    private ClientService clientService;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        clientService = mock(ClientService.class);
        clientController = new ClientController(clientService);
    }

    @Test
    void testGetClientJobStats_ReturnsStatsList() {
        ClientJobStatsDTO dto = ClientTestDataFactory.createSampleClientJobStatsDTO();

        when(clientService.getClientJobStats()).thenReturn(List.of(dto));

        ResponseEntity<List<ClientJobStatsDTO>> response = clientController.getClientJobStats();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getClientId()).isEqualTo(dto.getClientId());
        assertThat(response.getBody().get(0).getJobCounts()).isEqualTo(dto.getJobCounts());
    }

    @Test
    void testGetClientJobStats_EmptyList() {
        when(clientService.getClientJobStats()).thenReturn(List.of());

        ResponseEntity<List<ClientJobStatsDTO>> response = clientController.getClientJobStats();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEmpty();
    }
}

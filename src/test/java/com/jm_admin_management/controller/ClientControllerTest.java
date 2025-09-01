package com.jm_admin_management.controller;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.service.ClientService;
import com.jm_admin_management.test_utils.factory.ClientTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

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
        UUID clientId = UUID.randomUUID();
        // Provide all required arguments for createClientJobProjection
        String clientName = "Test Client";
        String clientEmail = "test@example.com";
        String clientPhone = "1234567890";
        JobPostingStatus status = JobPostingStatus.IN_PROGRESS;
        Long jobCounts = 5L;
        java.util.Map<JobPostingStatus, Long> jobCountsMap = new java.util.HashMap<>();
        jobCountsMap.put(status, jobCounts);
        ClientJobStatsDTO dto = new ClientJobStatsDTO(
            clientId, clientName, clientEmail, clientPhone, jobCountsMap
        );

        when(clientService.getClientJobStats()).thenReturn(List.of(dto));

        ResponseEntity<List<ClientJobStatsDTO>> response = clientController.getClientJobStats();

        assertThat(response.getBody().get(0).getClientId()).isEqualTo(clientId);
        assertThat(response.getBody().get(0).getJobCounts()).isEqualTo(dto.getJobCounts());
        assertThat(response.getBody().get(0).getJobCounts().get(status)).isEqualTo(jobCounts);
        assertThat(response.getBody().get(0).getClientId()).isEqualTo(clientId);
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

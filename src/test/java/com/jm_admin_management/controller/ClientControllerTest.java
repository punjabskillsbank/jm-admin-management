package com.jm_admin_management.controller;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    @Test
    void getClientJobStats_returnsStatsList() {
        ClientService clientService = mock(ClientService.class);
        ClientController controller = new ClientController(clientService);

        UUID clientId = UUID.randomUUID();
        ClientJobStatsDTO dto = new ClientJobStatsDTO();
        dto.setClientId(clientId);
        dto.setProfilePhotoURL("url");
        dto.setIndustry("industry");
        dto.setCompanyName("company");

        Map<JobPostingStatus, Long> jobCounts = new EnumMap<>(JobPostingStatus.class);
        for (JobPostingStatus status : JobPostingStatus.values()) {
            jobCounts.put(status, 0L);
        }
        jobCounts.put(JobPostingStatus.OPEN, 5L); // Use the correct enum constant name
        dto.setJobCounts(jobCounts);

        List<ClientJobStatsDTO> statsList = Collections.singletonList(dto);

        when(clientService.getClientJobStats()).thenReturn(statsList);

        ResponseEntity<List<ClientJobStatsDTO>> response = controller.getClientJobStats();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(statsList, response.getBody());
        verify(clientService, times(1)).getClientJobStats();

        // Check jobCounts initialization and values
        ClientJobStatsDTO resultDto = response.getBody().get(0);
        for (JobPostingStatus status : JobPostingStatus.values()) {
            assertNotNull(resultDto.getJobCounts().get(status));
        }
        assertEquals(5L, resultDto.getJobCounts().get(JobPostingStatus.OPEN)); // Use the correct enum constant name
    }

    @Test
    void getClientJobStats_returnsEmptyList() {
        ClientService clientService = mock(ClientService.class);
        ClientController controller = new ClientController(clientService);

        when(clientService.getClientJobStats()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ClientJobStatsDTO>> response = controller.getClientJobStats();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(clientService, times(1)).getClientJobStats();
    }

    @Test
    void getClientJobStats_serviceReturnsNull() {
        ClientService clientService = mock(ClientService.class);
        ClientController controller = new ClientController(clientService);

        when(clientService.getClientJobStats()).thenReturn(null);

        ResponseEntity<List<ClientJobStatsDTO>> response = controller.getClientJobStats();

        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(clientService, times(1)).getClientJobStats();
    }
}
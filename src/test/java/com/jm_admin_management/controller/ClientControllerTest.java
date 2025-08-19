package com.jm_admin_management.controller;

import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




class ClientControllerTest {

    @Test
    void getClientJobStats_returnsStatsList() {
        ClientService clientService = mock(ClientService.class);
        ClientController controller = new ClientController(clientService);

        ClientJobStatsDTO dto1 = new ClientJobStatsDTO();
        ClientJobStatsDTO dto2 = new ClientJobStatsDTO();
        List<ClientJobStatsDTO> statsList = Arrays.asList(dto1, dto2);

        when(clientService.getClientJobStats()).thenReturn(statsList);

        ResponseEntity<List<ClientJobStatsDTO>> response = controller.getClientJobStats();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(statsList, response.getBody());
        verify(clientService, times(1)).getClientJobStats();
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
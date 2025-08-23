package com.jm_admin_management.serviceImpl;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobProjectionDTO;
import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.repository.ClientRepository;
import com.jm_admin_management.serviceImpl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    void getClientJobStats_EmptyList_ReturnsEmptyList() {
        when(clientRepository.findClientsWithJobStats()).thenReturn(new ArrayList<>());
        
        List<ClientJobStatsDTO> result = clientService.getClientJobStats();
        
        assertTrue(result.isEmpty());
        verify(clientRepository).findClientsWithJobStats();
    }

    @Test
    void getClientJobStats_SingleClientWithOneStatus_ReturnsCorrectStats() {
        UUID clientId = UUID.randomUUID();
        List<ClientJobProjectionDTO> projections = new ArrayList<>();
        ClientJobProjectionDTO projection = mock(ClientJobProjectionDTO.class);
        
        when(projection.getClientId()).thenReturn(clientId);
        when(projection.getProfilePhotoURL()).thenReturn("photo.jpg");
        when(projection.getIndustry()).thenReturn("IT");
        when(projection.getCompanyName()).thenReturn("Tech Co");
        when(projection.getStatus()).thenReturn(JobPostingStatus.OPEN);
        when(projection.getCount()).thenReturn(5L);
        
        projections.add(projection);
        when(clientRepository.findClientsWithJobStats()).thenReturn(projections);
        
        List<ClientJobStatsDTO> result = clientService.getClientJobStats();
        
        assertEquals(1, result.size());
        ClientJobStatsDTO dto = result.get(0);
        assertEquals(clientId, dto.getClientId());
        assertEquals("photo.jpg", dto.getProfilePhotoURL());
        assertEquals("IT", dto.getIndustry());
        assertEquals("Tech Co", dto.getCompanyName());
        assertEquals(5L, dto.getJobCounts().get(JobPostingStatus.OPEN));
        
        // All other statuses should be initialized to 0
        for (JobPostingStatus status : JobPostingStatus.values()) {
            if (status != JobPostingStatus.OPEN) {
                assertEquals(0L, dto.getJobCounts().get(status));
            }
        }
    }

    @Test
    void getClientJobStats_SingleClientMultipleStatuses_ReturnsCorrectStats() {
        UUID clientId = UUID.randomUUID();
        List<ClientJobProjectionDTO> projections = new ArrayList<>();
        
        ClientJobProjectionDTO projection1 = mock(ClientJobProjectionDTO.class);
        when(projection1.getClientId()).thenReturn(clientId);
        when(projection1.getProfilePhotoURL()).thenReturn("photo.jpg");
        when(projection1.getIndustry()).thenReturn("IT");
        when(projection1.getCompanyName()).thenReturn("Tech Co");
        when(projection1.getStatus()).thenReturn(JobPostingStatus.OPEN);
        when(projection1.getCount()).thenReturn(5L);
        
        ClientJobProjectionDTO projection2 = mock(ClientJobProjectionDTO.class);
        when(projection2.getClientId()).thenReturn(clientId);
        when(projection2.getProfilePhotoURL()).thenReturn("photo.jpg");
        when(projection2.getIndustry()).thenReturn("IT");
        when(projection2.getCompanyName()).thenReturn("Tech Co");
        when(projection2.getStatus()).thenReturn(JobPostingStatus.CLOSED);
        when(projection2.getCount()).thenReturn(3L);
        
        projections.add(projection1);
        projections.add(projection2);
        when(clientRepository.findClientsWithJobStats()).thenReturn(projections);
        
        List<ClientJobStatsDTO> result = clientService.getClientJobStats();
        
        assertEquals(1, result.size());
        ClientJobStatsDTO dto = result.get(0);
        assertEquals(5L, dto.getJobCounts().get(JobPostingStatus.OPEN));
        assertEquals(3L, dto.getJobCounts().get(JobPostingStatus.CLOSED));
    }

    @Test
    void getClientJobStats_MultipleClients_ReturnsCorrectStats() {
        UUID clientId1 = UUID.randomUUID();
        UUID clientId2 = UUID.randomUUID();
        List<ClientJobProjectionDTO> projections = new ArrayList<>();
        
        ClientJobProjectionDTO projection1 = mock(ClientJobProjectionDTO.class);
        when(projection1.getClientId()).thenReturn(clientId1);
        when(projection1.getProfilePhotoURL()).thenReturn("photo1.jpg");
        when(projection1.getIndustry()).thenReturn("IT");
        when(projection1.getCompanyName()).thenReturn("Tech Co");
        when(projection1.getStatus()).thenReturn(JobPostingStatus.OPEN);
        when(projection1.getCount()).thenReturn(5L);
        
        ClientJobProjectionDTO projection2 = mock(ClientJobProjectionDTO.class);
        when(projection2.getClientId()).thenReturn(clientId2);
        when(projection2.getProfilePhotoURL()).thenReturn("photo2.jpg");
        when(projection2.getIndustry()).thenReturn("Finance");
        when(projection2.getCompanyName()).thenReturn("Finance Co");
        when(projection2.getStatus()).thenReturn(JobPostingStatus.DRAFT);
        when(projection2.getCount()).thenReturn(2L);
        
        projections.add(projection1);
        projections.add(projection2);
        when(clientRepository.findClientsWithJobStats()).thenReturn(projections);
        
        List<ClientJobStatsDTO> result = clientService.getClientJobStats();
        
        assertEquals(2, result.size());
        
        // Find DTOs by client ID
        ClientJobStatsDTO dto1 = result.stream()
                .filter(dto -> dto.getClientId().equals(clientId1))
                .findFirst()
                .orElse(null);
        
        ClientJobStatsDTO dto2 = result.stream()
                .filter(dto -> dto.getClientId().equals(clientId2))
                .findFirst()
                .orElse(null);
        
        assertNotNull(dto1);
        assertNotNull(dto2);
        
        assertEquals(5L, dto1.getJobCounts().get(JobPostingStatus.OPEN));
        assertEquals(2L, dto2.getJobCounts().get(JobPostingStatus.DRAFT));
    }
}
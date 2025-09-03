package com.jm_admin_management.serviceImpl;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.repository.ClientRepository;
import com.jm_admin_management.test_utils.factory.ClientTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    private ClientRepository clientRepository;
    private ModelMapper modelMapper;
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        modelMapper = mock(ModelMapper.class);
        clientService = new ClientServiceImpl(clientRepository, modelMapper);
        stubModelMapper(); // ✅ set up once for all tests
    }

    private void stubModelMapper() {
        when(modelMapper.map(any(), eq(ClientJobStatsDTO.class)))
                .thenAnswer(invocation -> {
                    var src = invocation.getArgument(0, com.jm_admin_management.dto.ClientJobProjectionDTO.class);
                    var dto = new ClientJobStatsDTO();
                    dto.setClientId(src.getClientId());
                    return dto;
                });
    }

    @Test
    void testGetClientJobStats_withValidData() {
        UUID clientId = UUID.randomUUID();
        var projections = ClientTestDataFactory.createProjectionsForSingleClient(clientId);

        when(clientRepository.findClientsWithJobStats()).thenReturn(projections);

        List<ClientJobStatsDTO> result = clientService.getClientJobStats();

        assertEquals(1, result.size());
        ClientJobStatsDTO dto = result.get(0);
        assertEquals(clientId, dto.getClientId());
        assertEquals(3L, dto.getJobCounts().get(JobPostingStatus.OPEN));
        assertEquals(1L, dto.getJobCounts().get(JobPostingStatus.CLOSED));
    }

    @Test
    void testGetClientJobStats_withEmptyList() {
        when(clientRepository.findClientsWithJobStats())
                .thenReturn(ClientTestDataFactory.createEmptyProjections(UUID.randomUUID()));

        List<ClientJobStatsDTO> result = clientService.getClientJobStats();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetClientJobStats_multipleClients() {
        UUID clientId1 = UUID.randomUUID();
        UUID clientId2 = UUID.randomUUID();
        var projections = ClientTestDataFactory.createProjectionsForMultipleClients(clientId1, clientId2);

        when(clientRepository.findClientsWithJobStats()).thenReturn(projections);

        List<ClientJobStatsDTO> result = clientService.getClientJobStats();

        assertEquals(2, result.size());
        Set<UUID> ids = new HashSet<>();
        for (ClientJobStatsDTO dto : result) {
            ids.add(dto.getClientId());
            if (dto.getClientId().equals(clientId1)) {
                assertEquals(2L, dto.getJobCounts().get(JobPostingStatus.OPEN));
            } else if (dto.getClientId().equals(clientId2)) {
                assertEquals(5L, dto.getJobCounts().get(JobPostingStatus.DRAFT));
            }
        }
    }

    @Test
    void testGetClientJobStats_duplicateStatuses() {
        UUID clientId = UUID.randomUUID();
        var projections = ClientTestDataFactory.createProjectionsWithDuplicateStatuses(clientId);

        when(clientRepository.findClientsWithJobStats()).thenReturn(projections);

        List<ClientJobStatsDTO> result = clientService.getClientJobStats();

        assertEquals(1, result.size());
        ClientJobStatsDTO dto = result.get(0);
        // Last OPEN value (5L) overwrites previous (2L)
        assertEquals(5L, dto.getJobCounts().get(JobPostingStatus.OPEN));
    }
}

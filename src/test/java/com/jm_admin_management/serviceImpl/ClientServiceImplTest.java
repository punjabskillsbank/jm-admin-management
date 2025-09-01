package com.jm_admin_management.serviceImpl;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobProjectionDTO;
import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.repository.ClientRepository;
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
    }

    @Test
    void testGetClientJobStats_withValidData() {
        UUID clientId = UUID.randomUUID();

        ClientJobProjectionDTO projection1 = new ClientJobProjectionDTO(
                clientId,
                "Test Client",
                null,
                null,
                JobPostingStatus.OPEN,
                3L
        );
        ClientJobProjectionDTO projection2 = new ClientJobProjectionDTO(
                clientId,
                "Test Client",
                null,
                null,
                JobPostingStatus.CLOSED,
                1L
        );

        when(modelMapper.map(any(ClientJobProjectionDTO.class), eq(ClientJobStatsDTO.class)))
                .thenAnswer(invocation -> {
                    ClientJobProjectionDTO src = invocation.getArgument(0);
                    ClientJobStatsDTO dto = new ClientJobStatsDTO();
                    dto.setClientId(src.getClientId());
                    return dto;
                });

        when(clientRepository.findClientsWithJobStats()).thenReturn(List.of(projection1, projection2));

        List<ClientJobStatsDTO> result = clientService.getClientJobStats();

        assertEquals(1, result.size());

        ClientJobStatsDTO dto = result.get(0);
        assertEquals(clientId, dto.getClientId());

        assertEquals(3L, dto.getJobCounts().get(JobPostingStatus.OPEN));
        assertEquals(1L, dto.getJobCounts().get(JobPostingStatus.CLOSED));
        Arrays.stream(JobPostingStatus.values())
                .filter(s -> s != JobPostingStatus.OPEN && s != JobPostingStatus.CLOSED)
                .forEach(s -> assertEquals(0L, dto.getJobCounts().get(s)));
    }

    @Test
    void testGetClientJobStats_withEmptyList() {
        when(clientRepository.findClientsWithJobStats()).thenReturn(Collections.emptyList());

        List<ClientJobStatsDTO> result = clientService.getClientJobStats();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetClientJobStats_multipleClients() {
        UUID clientId1 = UUID.randomUUID();
        UUID clientId2 = UUID.randomUUID();

        ClientJobProjectionDTO projection1 = new ClientJobProjectionDTO(
                clientId1, "Client1", null, null, JobPostingStatus.OPEN, 2L
        );
        ClientJobProjectionDTO projection2 = new ClientJobProjectionDTO(
                clientId2, "Client2", null, null, JobPostingStatus.CLOSED, 5L
        );

        when(modelMapper.map(any(ClientJobProjectionDTO.class), eq(ClientJobStatsDTO.class)))
                .thenAnswer(invocation -> {
                    ClientJobProjectionDTO src = invocation.getArgument(0);
                    ClientJobStatsDTO dto = new ClientJobStatsDTO();
                    dto.setClientId(src.getClientId());
                    return dto;
                });

        when(clientRepository.findClientsWithJobStats()).thenReturn(List.of(projection1, projection2));

        List<ClientJobStatsDTO> result = clientService.getClientJobStats();

        assertEquals(2, result.size());
        Set<UUID> ids = new HashSet<>();
        for (ClientJobStatsDTO dto : result) {
            ids.add(dto.getClientId());
            if (dto.getClientId().equals(clientId1)) {
                assertEquals(2L, dto.getJobCounts().get(JobPostingStatus.OPEN));
            } else if (dto.getClientId().equals(clientId2)) {
                assertEquals(5L, dto.getJobCounts().get(JobPostingStatus.CLOSED));
            }
        }
        assertTrue(ids.contains(clientId1));
        assertTrue(ids.contains(clientId2));
    }

    @Test
    void testGetClientJobStats_duplicateStatuses() {
        UUID clientId = UUID.randomUUID();

        ClientJobProjectionDTO projection1 = new ClientJobProjectionDTO(
                clientId, "Client", null, null, JobPostingStatus.OPEN, 2L
        );
        ClientJobProjectionDTO projection2 = new ClientJobProjectionDTO(
                clientId, "Client", null, null, JobPostingStatus.OPEN, 5L
        );

        when(modelMapper.map(any(ClientJobProjectionDTO.class), eq(ClientJobStatsDTO.class)))
                .thenAnswer(invocation -> {
                    ClientJobProjectionDTO src = invocation.getArgument(0);
                    ClientJobStatsDTO dto = new ClientJobStatsDTO();
                    dto.setClientId(src.getClientId());
                    return dto;
                });

        when(clientRepository.findClientsWithJobStats()).thenReturn(List.of(projection1, projection2));

        List<ClientJobStatsDTO> result = clientService.getClientJobStats();

        assertEquals(1, result.size());
        ClientJobStatsDTO dto = result.get(0);
        // The last value should overwrite previous for the same status
        assertEquals(5L, dto.getJobCounts().get(JobPostingStatus.OPEN));
    }}

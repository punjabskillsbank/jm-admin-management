package com.jm_admin_management.serviceImpl;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobStatsDTO;
import com.jm_admin_management.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {

    private ClientRepository clientRepository;
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    void testGetClientJobStats_ReturnsCorrectStats() {
        UUID clientId = UUID.randomUUID();
        String profilePhotoURL = "http://photo.url";
        String industry = "Tech";
        String companyName = "Acme";
        JobPostingStatus statusOpen = JobPostingStatus.OPEN;
        JobPostingStatus statusClosed = JobPostingStatus.CLOSED;
        Long countOpen = 5L;
        Long countClosed = 2L;

        List<Object[]> mockResults = Arrays.asList(
                new Object[]{clientId, profilePhotoURL, industry, companyName, statusOpen.name(), countOpen},
                new Object[]{clientId, profilePhotoURL, industry, companyName, statusClosed.name(), countClosed}
        );

        when(clientRepository.findClientsWithJobCounts()).thenReturn(mockResults);

        List<ClientJobStatsDTO> stats = clientService.getClientJobStats();

        assertEquals(1, stats.size());
        ClientJobStatsDTO dto = stats.get(0);
        assertEquals(clientId, dto.getClientId());
        assertEquals(profilePhotoURL, dto.getProfilePhotoURL());
        assertEquals(industry, dto.getIndustry());
        assertEquals(companyName, dto.getCompanyName());
        assertEquals(countOpen, dto.getJobCounts().get(statusOpen));
        assertEquals(countClosed, dto.getJobCounts().get(statusClosed));
    }

    @Test
    void testGetClientJobStats_EmptyResults() {
        when(clientRepository.findClientsWithJobCounts()).thenReturn(Collections.emptyList());
        List<ClientJobStatsDTO> stats = clientService.getClientJobStats();
        assertTrue(stats.isEmpty());
    }

    @Test
    void testGetClientJobStats_MultipleClients() {
        UUID clientId1 = UUID.randomUUID();
        UUID clientId2 = UUID.randomUUID();

        List<Object[]> mockResults = Arrays.asList(
                new Object[]{clientId1, "url1", "Industry1", "Company1", JobPostingStatus.OPEN.name(), 3L},
                new Object[]{clientId2, "url2", "Industry2", "Company2", JobPostingStatus.CLOSED.name(), 1L}
        );

        when(clientRepository.findClientsWithJobCounts()).thenReturn(mockResults);

        List<ClientJobStatsDTO> stats = clientService.getClientJobStats();

        assertEquals(2, stats.size());
        Set<UUID> ids = new HashSet<>();
        for (ClientJobStatsDTO dto : stats) {
            ids.add(dto.getClientId());
        }
        assertTrue(ids.contains(clientId1));
        assertTrue(ids.contains(clientId2));
    }

    @Test
    void testGetClientJobStats_JobCountsAreAggregatedCorrectly() {
        UUID clientId = UUID.randomUUID();

        List<Object[]> mockResults = Arrays.asList(
                new Object[]{clientId, "url", "Industry", "Company", JobPostingStatus.OPEN.name(), 1L},
                new Object[]{clientId, "url", "Industry", "Company", JobPostingStatus.OPEN.name(), 2L},
                new Object[]{clientId, "url", "Industry", "Company", JobPostingStatus.CLOSED.name(), 3L}
        );

        when(clientRepository.findClientsWithJobCounts()).thenReturn(mockResults);

        List<ClientJobStatsDTO> stats = clientService.getClientJobStats();

        assertEquals(1, stats.size());
        ClientJobStatsDTO dto = stats.get(0);
        assertEquals(2L, dto.getJobCounts().get(JobPostingStatus.OPEN)); // last value should be present
        assertEquals(3L, dto.getJobCounts().get(JobPostingStatus.CLOSED));
    }
}

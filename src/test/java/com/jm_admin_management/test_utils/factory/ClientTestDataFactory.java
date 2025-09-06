package com.jm_admin_management.test_utils.factory;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobProjectionDTO;
import com.jm_admin_management.dto.ClientJobStatsDTO;

import java.util.*;

public class ClientTestDataFactory {


    public static ClientJobProjectionDTO createClientJobProjection(
            UUID clientId,
            String companyName,
            String industry,
            String profilePhotoURL,
            JobPostingStatus status,
            Long count
    ) {
        return new ClientJobProjectionDTO(
                clientId,
                profilePhotoURL,
                industry,
                companyName,
                status,
                count
        );
    }

    public static ClientJobStatsDTO createClientJobStatsDTO(
            UUID clientId,
            String companyName,
            String clientEmail,
            String clientPhone,
            Map<JobPostingStatus, Long> jobCounts
    ) {
        return new ClientJobStatsDTO(clientId, companyName, clientEmail, clientPhone, jobCounts);
    }

    public static ClientJobStatsDTO createSampleClientJobStatsDTO() {
        UUID clientId = UUID.randomUUID();
        Map<JobPostingStatus, Long> counts = new HashMap<>();
        counts.put(JobPostingStatus.IN_PROGRESS, 5L);
        return createClientJobStatsDTO(
                clientId,
                "Test Client",
                "test@example.com",
                "1234567890",
                counts
        );
    }


    public static List<ClientJobProjectionDTO> createProjectionsForSingleClient(UUID clientId) {
        return List.of(
                createClientJobProjection(clientId, "Tech Co", "IT", "photo.jpg", JobPostingStatus.OPEN, 3L),
                createClientJobProjection(clientId, "Tech Co", "IT", "photo.jpg", JobPostingStatus.CLOSED, 1L)
        );
    }

    public static List<ClientJobProjectionDTO> createProjectionsForMultipleClients(UUID clientId1, UUID clientId2) {
        return List.of(
                createClientJobProjection(clientId1, "Alpha Inc", "Finance", "alpha.jpg", JobPostingStatus.OPEN, 2L),
                createClientJobProjection(clientId2, "Beta Ltd", "Healthcare", "beta.jpg", JobPostingStatus.DRAFT, 5L)
        );
    }

    public static List<ClientJobProjectionDTO> createProjectionsWithDuplicateStatuses(UUID clientId) {
        return List.of(
                createClientJobProjection(clientId, "Client", "Retail", "client.jpg", JobPostingStatus.OPEN, 2L),
                createClientJobProjection(clientId, "Client", "Retail", "client.jpg", JobPostingStatus.OPEN, 5L)
        );
    }

    public static List<ClientJobProjectionDTO> createEmptyProjections(UUID clientId) {
        return List.of();
    }
}

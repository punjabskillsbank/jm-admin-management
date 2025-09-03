package com.jm_admin_management.test_utils.factory;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobProjectionDTO;

import java.util.List;
import java.util.UUID;

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
                companyName,
                industry,
                profilePhotoURL,
                status,
                count
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

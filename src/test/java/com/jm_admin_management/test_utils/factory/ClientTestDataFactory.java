package com.jm_admin_management.test_utils.factory;

import com.common.enums.JobPostingStatus;
import com.jm_admin_management.dto.ClientJobProjectionDTO;
import org.mockito.Mockito;

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
        ClientJobProjectionDTO projection = Mockito.mock(ClientJobProjectionDTO.class);

        Mockito.when(projection.getClientId()).thenReturn(clientId);
        Mockito.when(projection.getCompanyName()).thenReturn(companyName);
        Mockito.when(projection.getIndustry()).thenReturn(industry);
        Mockito.when(projection.getProfilePhotoURL()).thenReturn(profilePhotoURL);
        Mockito.when(projection.getStatus()).thenReturn(status);
        Mockito.when(projection.getCount()).thenReturn(count);

        return projection;
    }
}

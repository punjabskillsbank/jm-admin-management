package com.jm_admin_management.dto;

import com.common.enums.JobPostingStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ClientJobProjectionDTO {
    private final UUID clientId;
    private final String profilePhotoURL;
    private final String industry;
    private final String companyName;
    private final JobPostingStatus status;
    private final Long count;

    public ClientJobProjectionDTO(UUID clientId,
                                  String profilePhotoURL,
                                  String industry,
                                  String companyName,
                                  JobPostingStatus status,
                                  Long count) {
        this.clientId = clientId;
        this.profilePhotoURL = profilePhotoURL;
        this.industry = industry;
        this.companyName = companyName;
        this.status = status;
        this.count = count;
    }
}

package com.jm_admin_management.dto;

import com.common.enums.JobPostingStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClientJobProjectionDTO {
    private UUID clientId;
    private String profilePhotoURL;
    private String industry;
    private String companyName;
    private JobPostingStatus status;
    private Long count;
}

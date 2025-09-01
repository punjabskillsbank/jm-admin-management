package com.jm_admin_management.dto;

import com.common.enums.JobPostingStatus;
import lombok.*;

import java.util.Map;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class ClientJobStatsDTO {
    private UUID clientId;
    private String profilePhotoURL;
    private String industry;
    private String companyName;
    private Map<JobPostingStatus, Long> jobCounts;
}

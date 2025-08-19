package com.jm_admin_management.dto;

import lombok.Data;
import java.util.Map;
import java.util.UUID;

@Data
public class ClientJobStatsDTO {
    private UUID clientId;
    private String profilePhotoURL;
    private String industry;
    private String companyName;
    private Map<String, Long> jobCounts;
}
